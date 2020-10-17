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
@RequestMapping("/auctions")
public class BidAuctionController {
	
	@Autowired
	private BidAuctionRepository repository;
	
	public BidAuctionController() {
		
	}
	
	@PostMapping("/{id}/bids")
	@ResponseStatus(HttpStatus.CREATED)
	public BidAuction adicionar(@PathVariable Long id,
			@RequestBody BidAuction bid) {
			
		bid.setAuctionId(id);
		
		return repository.save(bid);
	}
	
	@GetMapping("/{id}/bids")
	public List<BidAuction> listar() {			
		return repository.findAll();
	}
	
	@GetMapping("/{id}/bids/{bidId}")
	public ResponseEntity<BidAuction> buscar(@PathVariable Long id,
			@PathVariable Long bidId) {
		
		Optional<BidAuction> bid = repository.findById(bidId);
		
		if (bid.isPresent()) {
			return ResponseEntity.ok(bid.get());
		}
		
		return ResponseEntity.notFound().build(); 
	}
	
	@PutMapping("/{id}/bids/{bidId}")
	public ResponseEntity<BidAuction> atualizar(@PathVariable Long id,
			@RequestBody BidAuction bid, 
			@PathVariable Long bidId) {
		
		if (!repository.existsById(bidId)) {
			return ResponseEntity.notFound().build();
		}
		
		bid.setId(bidId);
		bid = repository.save(bid);
		
		return ResponseEntity.ok(bid);
	}
	
	@DeleteMapping("/{id}/bids/{bidId}")
	public ResponseEntity<Void> remover(@PathVariable Long id,
			@PathVariable Long bidId) {
		if (!repository.existsById(bidId)) {
			return ResponseEntity.notFound().build();
		}
		
		repository.deleteById(bidId);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}/bids/results")
	public ResponseEntity<BidAuction> result(@PathVariable Long id) {
				
		List<BidAuction> bids = repository.findAll();
		BidAuction bid = bids.get(0);
		for (BidAuction bidAuction : bids) {
			if (bidAuction.getAuctionId() == id ) {
				if ( bidAuction.getPrice().doubleValue() < bid.getPrice().doubleValue() ) {
					bid = bidAuction;
				}
			}
		}		
		
		if (bid != null) {
			return ResponseEntity.ok(bid);
		}
		
		return ResponseEntity.notFound().build(); 
	}

}
