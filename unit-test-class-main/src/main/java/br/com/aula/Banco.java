package br.com.aula;

import java.util.ArrayList;
import java.util.List;

import br.com.aula.exception.ContaComNomeJaExistente;
import br.com.aula.exception.ContaComNumueroInvalido;
import br.com.aula.exception.ContaJaExistenteException;
import br.com.aula.exception.ContaNaoExistenteException;
import br.com.aula.exception.ContaSemSaldoException;
import br.com.aula.exception.TransferenciaNegativa;

public class Banco {

	private List<Conta> contas = new ArrayList<Conta>();

	public Banco() {
	}

	public Banco(List<Conta> contas) {
		this.contas = contas;
	}


	public void cadastrarConta(Conta conta) throws ContaJaExistenteException, ContaComNomeJaExistente, ContaComNumueroInvalido {

		for (Conta c : contas) {
			boolean isNomeClienteIgual = c.getCliente().getNome().equals(conta.getCliente().getNome());
			boolean isNumeroContaIgual = c.getNumeroConta() == conta.getNumeroConta();
			boolean isNumerContaInvalido = c.getNumeroConta() > 9999999;

			if (isNomeClienteIgual ) {
				throw new ContaJaExistenteException();
			}
			
			if(isNumeroContaIgual) {
				throw new ContaComNomeJaExistente();

			}
			
			if(isNumerContaInvalido) {
				throw new ContaComNumueroInvalido();

			}
			
			
		}
		
		this.contas.add(conta);

	}

	public void efetuarTransferencia(int numeroContaOrigem, int numeroContaDestino, int valor)
			throws ContaNaoExistenteException, ContaSemSaldoException, TransferenciaNegativa {

		Conta contaOrigem = this.obterContaPorNumero(numeroContaOrigem);
		Conta contaDestino = this.obterContaPorNumero(numeroContaDestino);

		boolean isContaOrigemExistente = contaOrigem != null;
		boolean isContaDestinoExistente = contaDestino != null;

		if (isContaOrigemExistente && isContaDestinoExistente) {

			boolean isContaOrigemPoupan�a = contaOrigem.getTipoConta().equals(TipoConta.POUPANCA);
			boolean isSaldoContaOrigemNegativo = contaOrigem.getSaldo() - valor < 0;
			boolean isSaldoContaDestinoNegativo = contaOrigem.getSaldo() - valor < 0;
			
			
			if (isContaOrigemPoupan�a && isSaldoContaDestinoNegativo) {
				throw new TransferenciaNegativa();
			}


			if (isContaOrigemPoupan�a && isSaldoContaOrigemNegativo) {
				throw new ContaSemSaldoException();
			}

			contaOrigem.debitar(valor);
			contaDestino.creditar(valor);

		} else {
			throw new ContaNaoExistenteException();
		}
	}

	public Conta obterContaPorNumero(int numeroConta) {

		for (Conta c : contas) {
			if (c.getNumeroConta() == numeroConta) {
				return c;
			}
		}

		return null;
	}

	public List<Conta> obterContas() {
		return this.contas;
	}
}
