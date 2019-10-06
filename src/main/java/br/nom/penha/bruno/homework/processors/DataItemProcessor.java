package br.nom.penha.bruno.homework.processors;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import br.nom.penha.bruno.homework.entity.Data;


public class DataItemProcessor implements ItemProcessor<Data, Data> {

	private static final Logger LOG = LoggerFactory.getLogger(DataItemProcessor.class);
	
	@Override
	public Data process(Data transacaoParam) throws Exception {
 
		try {
	
			validarPagamentosFinanceiro(transacaoParam);
						
		} catch (Exception e) {			
			LOG.error("Ocorreu um erro no Processamento: " + e.getMessage(), e);
		}
		
		return transacaoParam;
	}
	
	/**
	 * Apenas o departamento Financeiro pode emitir pagamentos para terceiros.
	 * @param transacaoParam com a transação a ser tratada
	 */
	private void validarPagamentosFinanceiro(Data transacaoParam) {
		
		/*if(transacaoParam.getTipo().equals(TipoTransacaoEnum.PAGAMENTO.getDescricao())
				&& !transacaoParam.getRemetente().equals(RemetenteEnum.FINANCEIRO.getDescricao())){
			
			transacaoParam.setIrregularidade(IrregularidadeEnum.REGRA_1.getDescricao());
			if (LOG.isDebugEnabled()) {
				LOG.debug(String.format(FORMATO, MSG_ERRO, transacaoParam.getId(), PAGAMENTO_INDEVIDO));
			}
		}*/
	}


}
