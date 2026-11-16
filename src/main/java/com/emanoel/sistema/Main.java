package com.emanoel.sistema;

import com.emanoel.sistema.repository.FuncionarioRepository;
import com.emanoel.sistema.service.FuncionarioService;
import com.emanoel.sistema.view.FuncionarioView;

public class Main {
    public static void main(String[] args) {
        FuncionarioRepository repository = new FuncionarioRepository();
        FuncionarioService service = new FuncionarioService(repository);
        FuncionarioView view = new FuncionarioView(service);

        view.iniciar();
    }
}
