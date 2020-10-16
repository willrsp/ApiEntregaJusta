package com.example.demo;

import java.math.BigDecimal;
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
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductRepository repository;
	
	public ProductController() {
		
	}
	
	@GetMapping
	public List<Product> listar() {
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> buscar(@PathVariable Long id) {
		
		Optional<Product> product = repository.findById(id);
		
		if (product.isPresent()) {
			return ResponseEntity.ok(product.get());
		}
		
		return ResponseEntity.notFound().build(); 
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Product adicionar(@RequestBody Product product) {
				
		return repository.save(product);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> atualizar(@PathVariable Long id,
			@RequestBody Product product) {
		
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		product.setId(id);
		product = repository.save(product);
		
		return ResponseEntity.ok(product);
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
