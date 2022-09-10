package banco;

import java.sql.SQLException;

public class TestBanco {

	public static void main(String[] args) throws SQLException {
		Conta conta = new Conta("0001","45","pedrao","51456892");
		//conta.abrirConta(); Caso precise abrir conta
		
		conta.depositar(200);
		conta.sacar(1050);
		System.out.println("Saldo Atual: "+conta.consultarSaldo());
		conta.sacar(50);
		System.out.println("Saldo Atual: "+conta.consultarSaldo());
		conta.depositar(400);
		conta.sacar(450);
		System.out.println("Saldo Atual: "+conta.consultarSaldo());
		conta.depositar(500);
		conta.sacar(550);
		System.out.println("Saldo Atual: "+conta.consultarSaldo());
		
		conta.consultarExtrato();

	}
	
}//F
