package practice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import practice.model.NgheNghiep;

public interface NgheNghiepRepository extends CrudRepository<NgheNghiep, Long>{
	
	List<NgheNghiep> findByCode(String code);
}
