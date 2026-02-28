package controller;

import model.Categoria;
import model.Insumo;
import model.Obra;
import model.StorageType;
import repository.ConfigRepository;
import repository.DataRepository;

import java.util.ArrayList;
import java.util.List;

public class AppService {
    private final ConfigRepository configRepository;
    private final DataRepository dataRepository;
    private StorageType storageType;
    private List<Categoria> categorias;
    private List<Insumo> insumos;
    private List<Obra> obras;

    public AppService() {
        this.configRepository = new ConfigRepository();
        this.dataRepository = new DataRepository();
        this.storageType = this.configRepository.getStorageType();
        reload();
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
        this.configRepository.setStorageType(storageType);
        saveAll();
    }

    public void reload() {
        this.categorias = this.dataRepository.loadCategorias(this.storageType);
        this.insumos = this.dataRepository.loadInsumos(this.storageType);
        this.obras = this.dataRepository.loadObras(this.storageType);
    }

    public List<Categoria> getCategorias() {
        return new ArrayList<>(categorias);
    }

    public List<Insumo> getInsumos() {
        return new ArrayList<>(insumos);
    }

    public List<Obra> getObras() {
        return new ArrayList<>(obras);
    }

    public boolean addCategoria(Categoria categoria) {
        if (findCategoria(categoria.getId()) != null) {
            return false;
        }
        categorias.add(categoria);
        saveCategorias();
        return true;
    }

    public boolean updateCategoria(Categoria categoria) {
        Categoria current = findCategoria(categoria.getId());
        if (current == null) {
            return false;
        }
        current.setNombre(categoria.getNombre());
        saveCategorias();
        return true;
    }

    public boolean deleteCategoria(String id) {
        for (Insumo insumo : insumos) {
            if (insumo.getIdCategoria().equals(id)) {
                return false;
            }
        }
        boolean removed = categorias.removeIf(c -> c.getId().equals(id));
        if (removed) {
            saveCategorias();
        }
        return removed;
    }

    public boolean addInsumo(Insumo insumo) {
        if (findInsumo(insumo.getId()) != null || findCategoria(insumo.getIdCategoria()) == null) {
            return false;
        }
        insumos.add(insumo);
        saveInsumos();
        return true;
    }

    public boolean updateInsumo(Insumo insumo) {
        Insumo current = findInsumo(insumo.getId());
        if (current == null || findCategoria(insumo.getIdCategoria()) == null) {
            return false;
        }
        current.setNombre(insumo.getNombre());
        current.setIdCategoria(insumo.getIdCategoria());
        saveInsumos();
        return true;
    }

    public boolean deleteInsumo(String id) {
        boolean removed = insumos.removeIf(i -> i.getId().equals(id));
        if (removed) {
            saveInsumos();
        }
        return removed;
    }

    public String generateNextObraId() {
        int max = 0;
        for (Obra obra : obras) {
            String numeric = obra.getId().replaceAll("\\D", "");
            if (!numeric.isEmpty()) {
                int value = Integer.parseInt(numeric);
                if (value > max) {
                    max = value;
                }
            }
        }
        return String.format("OBR%03d", max + 1);
    }

    public boolean addObra(Obra obra) {
        if (findObra(obra.getId()) != null) {
            return false;
        }
        obras.add(obra);
        saveObras();
        return true;
    }

    public boolean updateObra(Obra obra) {
        Obra current = findObra(obra.getId());
        if (current == null) {
            return false;
        }
        current.setNombre(obra.getNombre());
        current.setDescripcion(obra.getDescripcion());
        saveObras();
        return true;
    }

    public boolean deleteObra(String id) {
        boolean removed = obras.removeIf(o -> o.getId().equals(id));
        if (removed) {
            saveObras();
        }
        return removed;
    }

    public String categoriaNombre(String idCategoria) {
        Categoria categoria = findCategoria(idCategoria);
        return categoria == null ? "" : categoria.getNombre();
    }

    private Categoria findCategoria(String id) {
        for (Categoria categoria : categorias) {
            if (categoria.getId().equals(id)) {
                return categoria;
            }
        }
        return null;
    }

    private Insumo findInsumo(String id) {
        for (Insumo insumo : insumos) {
            if (insumo.getId().equals(id)) {
                return insumo;
            }
        }
        return null;
    }

    private Obra findObra(String id) {
        for (Obra obra : obras) {
            if (obra.getId().equals(id)) {
                return obra;
            }
        }
        return null;
    }

    private void saveCategorias() {
        this.dataRepository.saveCategorias(this.storageType, this.categorias);
    }

    private void saveInsumos() {
        this.dataRepository.saveInsumos(this.storageType, this.insumos);
    }

    private void saveObras() {
        this.dataRepository.saveObras(this.storageType, this.obras);
    }

    private void saveAll() {
        saveCategorias();
        saveInsumos();
        saveObras();
    }
}
