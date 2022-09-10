package banco;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.time.LocalDate;


public class Movimentacao {
	Integer id_movimentacao;
	String tipo,descricao,num_agencia,num_conta;
	Double valor;
	LocalDate db_movimentacao;
	
	
	
	
	public Movimentacao(String tipo, String descricao, String num_agencia, String num_conta, Double valor) throws SQLException {
		super();
		this.tipo = tipo;
		this.descricao = descricao;
		this.num_agencia = num_agencia;
		this.num_conta = num_conta;
		this.valor = valor;

	}

	//Responsavel por mostrar oextrato na tela
	public boolean movimentar() throws SQLException{
		try {
			Connection con = Conexao.conectar();
			String sql = "Insert into tb_movimentacao values (NULL,?,?,?,CURDATE(),?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, tipo);
			stmt.setString(2,descricao);
			stmt.setDouble(3,valor);
			stmt.setString(4, num_agencia);
			stmt.setString(5, num_conta);
			stmt.execute();
			return true;
		}catch(Exception e){
			return false;
			
		}
		
	}
	
	
	

	
	}

	


