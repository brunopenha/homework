package br.nom.penha.bruno.homework.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileURLReader {

	private static final Logger LOG = LoggerFactory.getLogger(FileURLReader.class);
	private static final String ERRO = "Could not create directory ";

	private FileURLReader() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Unpack an archive from a URL
	 * 
	 * @param url
	 * @param targetDir
	 * @return the file to the url
	 * @throws IOException
	 */
	public static File unpackArchive(URL url, File targetDir) {

		File unZip = null;

		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}

		InputStream in = null;
		File zip = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(url.openStream(), 1024);
			zip = File.createTempFile("arc", ".zip", targetDir);
			out = new BufferedOutputStream(new FileOutputStream(zip));
			copyInputStream(in, out);
			unZip = unpackArchive(zip, targetDir);

		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {

			if (in != null) {
				try {
					in.close();
				} catch (IOException x) {
					LOG.error(x.getMessage());
				} finally {
					if (out != null) {
						try {
							out.close();
						} catch (IOException x) {
							LOG.error(x.getMessage());
						}
					}
				}
			}
		}

		return unZip;
	}

	/**
	 * Unpack a zip file
	 * 
	 * @param theFile
	 * @param targetDir
	 * @return the file
	 * @throws IOException
	 */
	public static File unpackArchive(File theFile, File targetDir) throws IOException {
		if (!theFile.exists()) {
			throw new IOException(theFile.getAbsolutePath() + " does not exist");
		}
		if (!buildDirectory(targetDir)) {
			throw new IOException(ERRO + targetDir);
		}
		
		try(ZipFile zipFile = new ZipFile(theFile)){
		
			for (Enumeration entries = zipFile.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				File file = new File(targetDir, File.separator + entry.getName());
				if (!buildDirectory(file.getParentFile())) {
					throw new IOException(ERRO + file.getParentFile());
				}
				if (!entry.isDirectory()) {
					copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(file)));
				} else {
					if (!buildDirectory(file)) {
						throw new IOException(ERRO + file);
					}
				}
			}
			
		}catch (IOException x) {
			LOG.error(x.getMessage());	
		}
		
		return theFile;
	}

	public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		while (len >= 0) {
			out.write(buffer, 0, len);
			len = in.read(buffer);
		}
		in.close();
		out.close();
	}

	public static boolean buildDirectory(File file) {
		return file.exists() || file.mkdirs();
	}

}