package com.project.Fabo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.Fabo.entity.Admin;
import com.project.Fabo.entity.Client;
import com.project.Fabo.entity.ClientUser;
import com.project.Fabo.service.ClientService;

@Controller
public class HomeController {
	
private ClientService clientService;
	
	public HomeController(ClientService clientService) {
		this.clientService = clientService;
	}
	
    @GetMapping("/home")    
    public String show(Model model) {
		Client client = new Client();
		model.addAttribute("client",client);
		ClientUser clientUser = new ClientUser();
		model.addAttribute("clientUser",clientUser);
		Admin admin = new Admin();
		model.addAttribute("admin",admin);
		 List<Client> clients = clientService.getAllClients();
	        model.addAttribute("clients", clients);
		return "home"; 
    }
}
