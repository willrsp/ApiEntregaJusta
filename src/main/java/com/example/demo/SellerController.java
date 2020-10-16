package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sellers")
public class SellerController {
	
	@Autowired
	private SellerRepository repository;
	
	public SellerController() {
		
	}
	
	@GetMapping
	public List<Seller> listar() {
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Seller> buscar(@PathVariable Long id) {
		
		Optional<Seller> seller = repository.findById(id);
		
		if (seller.isPresent()) {
			return ResponseEntity.ok(seller.get());
		}
		
		return ResponseEntity.notFound().build(); 
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Seller adicionar(@RequestBody Seller seller) {
				
		return repository.save(seller);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Seller> atualizar(@PathVariable Long id,
			@RequestBody Seller seller) {
		
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		seller.setId(id);
		seller = repository.save(seller);
		
		return ResponseEntity.ok(seller);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

}
