package com.example.buensaborback.business.service.Imp;

import com.example.buensaborback.business.service.Base.BaseServiceImp;
import com.example.buensaborback.business.service.SucursalService;
import com.example.buensaborback.domain.entities.Categoria;
import com.example.buensaborback.domain.entities.Empresa;
import com.example.buensaborback.domain.entities.Promocion;
import com.example.buensaborback.domain.entities.Sucursal;
import com.example.buensaborback.repositories.DomicilioRepository;
import com.example.buensaborback.repositories.EmpresaRepository;
import com.example.buensaborback.repositories.SucursalRepository;
import jakarta.transaction.Transactional;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SucursalServiceImpl extends BaseServiceImp<Sucursal,Long> implements SucursalService {
   @Autowired
   SucursalRepository sucursalRepository;
   @Autowired
   DomicilioRepository domicilioRepository;
   @Autowired
   EmpresaRepository empresaRepository;
    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImp.class);

    @Override
    @Transactional
    public Sucursal guardarSucursal(Sucursal sucursal) {
        var domicilio = sucursal.getDomicilio();
        if(domicilio.getId() != null){
            var domicilioBd = domicilioRepository.findById(domicilio.getId());
            domicilioBd.ifPresent(sucursal::setDomicilio);
        }
        var empresa = empresaRepository.findById(sucursal.getEmpresa().getId());
        if(empresa.isEmpty()){
            throw new RuntimeException("Empresa no encontrada");
        }

        return sucursalRepository.save(sucursal);
    }

    @Override
    public Sucursal actualizarSucursal(Long id, Sucursal sucursal) {
        var sucursalActualizar = sucursalRepository.findById(id);
        if (sucursalActualizar.isEmpty()) {
            throw new RuntimeException("Sucursal no encontrada: { id: " + id + " }");
        }
        Sucursal sucursalBd = sucursalActualizar.get();
        var empresa = empresaRepository.findById(sucursal.getEmpresa().getId());

        // Actualizar solo los campos necesarios
        sucursalBd.setNombre(sucursal.getNombre());
        sucursalBd.setDomicilio(sucursal.getDomicilio());
        sucursalBd.setEsCasaMatriz(sucursal.isEsCasaMatriz());
        sucursalBd.setHorarioApertura(sucursal.getHorarioApertura());
        sucursalBd.setHorarioCierre(sucursal.getHorarioCierre());

        // Manejar la imagen de la sucursal
        if (sucursal.getImagenSucursal() != null && sucursal.getImagenSucursal().getUrl() == null) {
            sucursalBd.setImagenSucursal(null);
        } else {
            sucursalBd.setImagenSucursal(sucursal.getImagenSucursal());
        }

        // Mantener las relaciones existentes
        sucursalBd.setEmpresa(empresa.get());

        // Actualización de la sucursal con las relaciones preservadas
        return sucursalRepository.save(sucursalBd);
    }

    @Override
    public List<Categoria> findCategoriasBySucursalId(Long sucursalId) {
        var sucursalExiste = sucursalRepository.findById(sucursalId);

        if(sucursalExiste.isEmpty()){
            throw new RuntimeException("Sucursal no encontrada: { id: " + sucursalId + " }");
        }

        List<Categoria> categorias = sucursalRepository.findCategoriasBySucursalId(sucursalId);
        Set<Categoria> filteredCategorias = new HashSet<>();
        for (Categoria categoria: categorias){
            if (categoria.getCategoriaPadre() == null) {
                filteredCategorias.add(categoria);
            }
        }
        filterSubcategorias(filteredCategorias, categorias);

        return new ArrayList<>(filteredCategorias);
    }

    @Override
    public List<Promocion> findPromocionBySucursalId(Long sucursalId) {
        var sucursalExiste = sucursalRepository.findById(sucursalId);

        if(sucursalExiste.isEmpty()){
            throw new RuntimeException("La sucursal con el id " + sucursalId + " no se ha encontrado");
        }

        return sucursalRepository.findPromocionBySucursalId(sucursalId);
    }

    public void filterSubcategorias(Set<Categoria> categorias, List<Categoria> categoriasBySucursal) {
        for (Categoria categoria : categorias) {
            System.out.println(categoria.getDenominacion());
            Set<Categoria> subcategorias = categoria.getSubCategorias();
            if (!subcategorias.isEmpty()) {
                Set<Categoria> filteredSubcategorias = new HashSet<>();
                for (Categoria subcategoria: subcategorias){
                    System.out.println(subcategoria.getDenominacion());
                    if (categoriasBySucursal.contains(subcategoria)) {
                        System.out.println("está en sucursal");
                        filteredSubcategorias.add(subcategoria);
                    }
                }
                categoria.setSubCategorias(filteredSubcategorias);
                filterSubcategorias(filteredSubcategorias, categoriasBySucursal);
            }

        }
    }

    @Override
    public List<Sucursal> findAllActiveByEmpresa(Long empresaId) {
        return sucursalRepository.findByEmpresa_Id(empresaId);
    }

    public Empresa getEmpresaBySucursalId(Long id) {
        Sucursal sucursal = sucursalRepository.findById(id).orElseThrow(() -> new RuntimeException("Empresa no encontrada."));
        return sucursal.getEmpresa();
    }
}
