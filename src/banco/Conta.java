package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conta {
	//Variaveis do objeto
	String numAgencia;
	String numConta;
	String cliente;
	String cpf;
	Double saldo;
	public Conta(String numAgencia, String numConta, String cliente, String cpf) {
		super();
		this.numAgencia = numAgencia;
		this.numConta = numConta;
		this.cliente = cliente;
		this.cpf = cpf;
	}
	
	//Responsavel por abrir a conta
	public boolean abrirConta() throws SQLException {//Retorna true se abriur e false se não abriu 
		try {
		//Conexão banco de dados
		Connection con = Conexao.conectar();
		String sql = "insert into tb_conta values (?,?,?,?,?);";//Codigo Sql
		PreparedStatement stmt = con.prepareStatement(sql);
		//Passagem de parametros
		stmt.setString(1,numAgencia);
		stmt.setString(2, numConta);
		stmt.setString(3, cliente);
		stmt.setString(4,cpf);
		stmt.setDouble(5, 0);
		//Execucao do codigo sql 
		stmt.execute();
		con.close();//Fechar conexao
		
		System.out.println("Conta Aberta");
		return true;//Retorna verdadeiro
		
		}catch(Exception e){
			System.out.println("Erro ao abrir conta");
			return false;///retorna false
		}
	}
	
	//Responsavel por sacar
	public boolean sacar(double valor) throws SQLException {
		 saldo = consultarSaldo();//Atualiza o saldo
		if(saldo >= valor) {//Só pode sacar se tiver saldo
			saldo-= valor;//Retira o valor do saque
			Connection con = Conexao.conectar();
			String sql = "UPDATE tb_conta set saldo = ? where num_conta = ?";//Atualiza o saldo
			PreparedStatement stmt = con.prepareCall(sql);
			stmt.setDouble(1, saldo);
			stmt.setString(2, numConta);
			stmt.execute();
			
			Movimentacao mov = new Movimentacao("D", "Saque efetuado de R$"+valor, numAgencia, numConta, valor);//Cria obj movimentacao
			mov.movimentar();//Salva os dados da movimentacao no  banco
			System.out.println("Saque realizado no valor de:"+valor);
			return true;//Retorna verdadeiro
		}else { 
			System.out.println("Erro ao sacar o valor de:"+valor+", saldo insuficiente.");
			return false;//retorna falso
		}
	}
	
	//Responsavel por depositar 
	public boolean depositar(double valor) throws SQLException {
		try {
		saldo = consultarSaldo();//Atualiza o saldo
		saldo += valor;//Aumenta o saldo
		String sql = "UPDATE tb_conta set saldo = ? where num_conta = ?";//Atualiza o saldo 
		Connection con = Conexao.conectar();
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setDouble(1, saldo);
		stmt.setString(2, numConta);
		stmt.execute();
		
		Movimentacao mov = new Movimentacao("C", "Deposito efetuado de R$"+valor, numAgencia, numConta, valor);//Cria obj saldo
		mov.movimentar();//Salva as informacoes de movimentacao 
		
		System.out.println("Deposito realizado no valor de:"+valor);
		return true;//retorna verdadeiro
		}catch(Exception e) {
			System.out.println("Erro ao depositar");
			return false;//retorna falso
		}
		
	}
	
	//responsavel por consultarSaldo
	public double consultarSaldo() throws SQLException {
		Connection con = Conexao.conectar();
		
		String sql =  "Select * from tb_conta where num_conta = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, numConta);
		
		stmt.execute();
		
		ResultSet result = stmt.executeQuery();
		
		if(result.next()) {//Se existir saldo
			
			double valorReturn = result.getDouble("saldo");
			con.close();
			return valorReturn;//retorna saldo
		}else {
			System.out.println("Erro ao consultar");
			con.close();
			return -1;//retorna -1
		}
		
	}
	
	//Responsavel por consultar Extrato bancario 
	public void consultarExtrato() throws SQLException {
		Connection con = Conexao.conectar();
		String sql = "Select * from tb_movimentacao where num_conta = ?";//Mostra todos os elementos do numero da conta
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, numConta);
		stmt.execute();
		
		ResultSet result = stmt.executeQuery();
		while(result.next()) {//Enquanto tiver resultados printa na tela
				System.out.println("----------------");
				
				System.out.println("Data: "+result.getDate("dt_movimentacao"));
			
				System.out.println("Tipo: "+result.getString("tipo"));
				
				System.out.println(result.getString("descricao"));
				
				System.out.println("Valor: "+result.getDouble("valor"));
				
				System.out.print("----------------\n\n");
		}
	}
		
	
	
	

	
	
	
	
	public String getNumAgencia() {
		return numAgencia;
	}
	public void setNumAgencia(String numAgencia) {
		this.numAgencia = numAgencia;
	}
	public String getNumConta() {
		return numConta;
	}
	public void setNumConta(String numConta) {
		this.numConta = numConta;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}


	@Override
	public String toString() {
		return "Conta [numAgencia=" + numAgencia + ", numConta=" + numConta + ", cliente=" + cliente + ", cpf=" + cpf
				+ ", saldo=" + saldo + "]";
	}
	
	
}
