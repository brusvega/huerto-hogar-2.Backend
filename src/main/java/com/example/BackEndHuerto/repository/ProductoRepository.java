package com.example.BackEndHuerto.repository;

import com.example.BackEndHuerto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
