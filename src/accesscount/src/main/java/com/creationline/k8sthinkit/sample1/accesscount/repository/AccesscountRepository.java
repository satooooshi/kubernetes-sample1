package com.creationline.k8sthinkit.sample1.accesscount.repository;

import com.creationline.k8sthinkit.sample1.accesscount.repository.entity.Accesscount;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccesscountRepository extends ReactiveSortingRepository<Accesscount, Long> { 
    
}
