package com.mycompany.createtextfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class CreateTextFile extends JFrame {
    private JTextField campoNumeroConta, campoNomeTitular, campoSaldo;
    private JButton botaoSalvar, botaoVisualizar;

    private ArrayList<AccountRecord> listaContas = new ArrayList<>();

    public CreateTextFile() {
        super("Gerenciador de Contas Bancárias");

        setLayout(new GridLayout(5, 2));

        add(new JLabel("Número da Conta:"));
        campoNumeroConta = new JTextField(10);
        add(campoNumeroConta);

        add(new JLabel("Nome do Titular:"));
        campoNomeTitular = new JTextField(10);
        add(campoNomeTitular);

        add(new JLabel("Saldo:"));
        campoSaldo = new JTextField(10);
        add(campoSaldo);

        botaoSalvar = new JButton("Salvar");
        add(botaoSalvar);

        botaoVisualizar = new JButton("Visualizar Contas");
        add(botaoVisualizar);

        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarConta();
            }
        });

        botaoVisualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizarContas();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
    }

    private void salvarConta() {
        try {
            int numeroConta = Integer.parseInt(campoNumeroConta.getText());
            String nomeTitular = campoNomeTitular.getText();
            double saldo = Double.parseDouble(campoSaldo.getText());

            AccountRecord conta = new AccountRecord(numeroConta, nomeTitular, saldo);
            listaContas.add(conta);

            try (FileWriter writer = new FileWriter("contas.txt", true)) {
                writer.write(conta.toString() + "\n");
                JOptionPane.showMessageDialog(this, "Conta salva com sucesso!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar a conta!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Entrada inválida. Por favor, insira os números corretamente.");
        }
    }

    private void visualizarContas() {
        JTextArea areaTexto = new JTextArea(10, 30);
        try (BufferedReader reader = new BufferedReader(new FileReader("contas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                areaTexto.append(linha + "\n");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar as contas!");
        }

        JFrame janelaVisualizacao = new JFrame("Contas Salvas");
        janelaVisualizacao.add(new JScrollPane(areaTexto));
        janelaVisualizacao.setSize(400, 300);
        janelaVisualizacao.setVisible(true);
    }

    public static void main(String[] args) {
        new CreateTextFile();
    }

    // Classe interna AccountRecord
    private class AccountRecord {
        private int numeroConta;
        private String nomeTitular;
        private double saldo;

        public AccountRecord(int numeroConta, String nomeTitular, double saldo) {
            this.numeroConta = numeroConta;
            this.nomeTitular = nomeTitular;
            this.saldo = saldo;
        }

        @Override
        public String toString() {
            return "Número da Conta: " + numeroConta + ", Nome do Titular: " + nomeTitular + ", Saldo: " + saldo;
        }
    }
}
