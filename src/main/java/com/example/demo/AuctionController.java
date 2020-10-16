package com.example.demo;

import java.util.Date;
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
@RequestMapping("/auctions")
public class AuctionController {
	
	@Autowired
	private AuctionRepository repository;
	
	public AuctionController() {
		
	}
	
	@GetMapping
	public List<Auction> listar() {			
		return repository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Auction adicionar(@RequestBody Auction auction) {
						
		return repository.save(auction);
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<Auction> buscar(@PathVariable Long id) {
		
		Optional<Auction> auction = repository.findById(id);
		
		if (auction.isPresent()) {
			return ResponseEntity.ok(auction.get());
		}
		
		return ResponseEntity.notFound().build(); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Auction> atualizar(@PathVariable Long id,
			@RequestBody Auction auction) {
		
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		auction.setId(id);
		auction = repository.save(auction);
		
		return ResponseEntity.ok(auction);
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
