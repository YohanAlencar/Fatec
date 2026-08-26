package br.com.tarefas.config.dao;

import br.com.tarefas.config.DatabaseConfig;
import br.com.tarefas.config.model.Tarefa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public void inserir(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (titulo, descricao, categoria_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setInt(3, tarefa.getCategoriaId());
            stmt.executeUpdate();
            System.out.println("✅ Tarefa cadastrada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir tarefa: " + e.getMessage());
        }
    }

    public List<Tarefa> listarTodas() {
        return buscarComFiltro("SELECT t.*, c.nome AS categoria_nome FROM tarefas t JOIN categorias c ON t.categoria_id = c.id ORDER BY t.id");
    }

    public List<Tarefa> listarPorCategoria(int categoriaId) {
        String sql = "SELECT t.*, c.nome AS categoria_nome FROM tarefas t JOIN categorias c ON t.categoria_id = c.id WHERE t.categoria_id = " + categoriaId + " ORDER BY t.id";
        return buscarComFiltro(sql);
    }

    public List<Tarefa> listarPorStatus(boolean concluida) {
        String sql = "SELECT t.*, c.nome AS categoria_nome FROM tarefas t JOIN categorias c ON t.categoria_id = c.id WHERE t.concluida = " + concluida + " ORDER BY t.id";
        return buscarComFiltro(sql);
    }

    private List<Tarefa> buscarComFiltro(String sql) {
        List<Tarefa> tarefas = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tarefa t = new Tarefa();
                t.setId(rs.getInt("id"));
                t.setTitulo(rs.getString("titulo"));
                t.setDescricao(rs.getString("descricao"));
                t.setConcluida(rs.getBoolean("concluida"));
                t.setCategoriaId(rs.getInt("categoria_id"));
                t.setNomeCategoria(rs.getString("categoria_nome"));
                tarefas.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar tarefas: " + e.getMessage());
        }
        return tarefas;
    }

    public void atualizar(int id, String novoTitulo, String novaDescricao) {
        String sql = "UPDATE tarefas SET titulo = ?, descricao = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoTitulo);
            stmt.setString(2, novaDescricao);
            stmt.setInt(3, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Tarefa atualizada com sucesso!");
            else System.out.println("⚠️ Tarefa não encontrada.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar tarefa: " + e.getMessage());
        }
    }

    public void marcarComoConcluida(int id) {
        String sql = "UPDATE tarefas SET concluida = TRUE WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Tarefa marcada como concluída!");
            else System.out.println("Tarefa não encontrada.");
        } catch (SQLException e) {
            System.err.println("Erro ao concluir tarefa: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Tarefa excluída com sucesso!");
            else System.out.println("Tarefa não encontrada.");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar tarefa: " + e.getMessage());
        }
    }
}