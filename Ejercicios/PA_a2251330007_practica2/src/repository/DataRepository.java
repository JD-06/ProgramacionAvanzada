package repository;

import model.Categoria;
import model.Insumo;
import model.Obra;
import model.StorageType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private final Path dataDir;

    public DataRepository() {
        this.dataDir = Path.of(System.getProperty("user.dir"), "data");
        try {
            Files.createDirectories(this.dataDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Categoria> loadCategorias(StorageType storageType) {
        return storageType == StorageType.XML ? loadCategoriasXml() : loadCategoriasTxt();
    }

    public void saveCategorias(StorageType storageType, List<Categoria> categorias) {
        if (storageType == StorageType.XML) {
            saveCategoriasXml(categorias);
        } else {
            saveCategoriasTxt(categorias);
        }
    }

    public List<Insumo> loadInsumos(StorageType storageType) {
        return storageType == StorageType.XML ? loadInsumosXml() : loadInsumosTxt();
    }

    public void saveInsumos(StorageType storageType, List<Insumo> insumos) {
        if (storageType == StorageType.XML) {
            saveInsumosXml(insumos);
        } else {
            saveInsumosTxt(insumos);
        }
    }

    public List<Obra> loadObras(StorageType storageType) {
        return storageType == StorageType.XML ? loadObrasXml() : loadObrasTxt();
    }

    public void saveObras(StorageType storageType, List<Obra> obras) {
        if (storageType == StorageType.XML) {
            saveObrasXml(obras);
        } else {
            saveObrasTxt(obras);
        }
    }

    private List<Categoria> loadCategoriasTxt() {
        List<Categoria> categorias = new ArrayList<>();
        for (String line : readLines(this.dataDir.resolve("categorias.txt"))) {
            String[] parts = line.split(",", 2);
            if (parts.length == 2) {
                categorias.add(new Categoria(parts[0].trim(), parts[1].trim()));
            }
        }
        return categorias;
    }

    private void saveCategoriasTxt(List<Categoria> categorias) {
        List<String> lines = new ArrayList<>();
        for (Categoria categoria : categorias) {
            lines.add(categoria.getId() + "," + categoria.getNombre());
        }
        writeLines(this.dataDir.resolve("categorias.txt"), lines);
    }

    private List<Insumo> loadInsumosTxt() {
        List<Insumo> insumos = new ArrayList<>();
        for (String line : readLines(this.dataDir.resolve("insumos.txt"))) {
            String[] parts = line.split(",", 3);
            if (parts.length == 3) {
                insumos.add(new Insumo(parts[0].trim(), parts[1].trim(), parts[2].trim()));
            }
        }
        return insumos;
    }

    private void saveInsumosTxt(List<Insumo> insumos) {
        List<String> lines = new ArrayList<>();
        for (Insumo insumo : insumos) {
            lines.add(insumo.getId() + "," + insumo.getNombre() + "," + insumo.getIdCategoria());
        }
        writeLines(this.dataDir.resolve("insumos.txt"), lines);
    }

    private List<Obra> loadObrasTxt() {
        List<Obra> obras = new ArrayList<>();
        for (String line : readLines(this.dataDir.resolve("obras.txt"))) {
            String[] parts = line.split(",", 3);
            if (parts.length == 3) {
                obras.add(new Obra(parts[0].trim(), parts[1].trim(), parts[2].trim()));
            }
        }
        return obras;
    }

    private void saveObrasTxt(List<Obra> obras) {
        List<String> lines = new ArrayList<>();
        for (Obra obra : obras) {
            lines.add(obra.getId() + "," + obra.getNombre() + "," + obra.getDescripcion());
        }
        writeLines(this.dataDir.resolve("obras.txt"), lines);
    }

    private List<Categoria> loadCategoriasXml() {
        List<Categoria> categorias = new ArrayList<>();
        Document document = loadDocument(this.dataDir.resolve("categorias.xml"));
        if (document == null) {
            return categorias;
        }
        NodeList nodeList = document.getElementsByTagName("categoria");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            categorias.add(new Categoria(getChildText(element, "id"), getChildText(element, "nombre")));
        }
        return categorias;
    }

    private void saveCategoriasXml(List<Categoria> categorias) {
        Document document = newDocument();
        Element root = document.createElement("categorias");
        document.appendChild(root);
        for (Categoria categoria : categorias) {
            Element item = document.createElement("categoria");
            appendChild(document, item, "id", categoria.getId());
            appendChild(document, item, "nombre", categoria.getNombre());
            root.appendChild(item);
        }
        writeDocument(document, this.dataDir.resolve("categorias.xml"));
    }

    private List<Insumo> loadInsumosXml() {
        List<Insumo> insumos = new ArrayList<>();
        Document document = loadDocument(this.dataDir.resolve("insumos.xml"));
        if (document == null) {
            return insumos;
        }
        NodeList nodeList = document.getElementsByTagName("insumo");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            insumos.add(new Insumo(getChildText(element, "id"), getChildText(element, "nombre"), getChildText(element, "idCategoria")));
        }
        return insumos;
    }

    private void saveInsumosXml(List<Insumo> insumos) {
        Document document = newDocument();
        Element root = document.createElement("insumos");
        document.appendChild(root);
        for (Insumo insumo : insumos) {
            Element item = document.createElement("insumo");
            appendChild(document, item, "id", insumo.getId());
            appendChild(document, item, "nombre", insumo.getNombre());
            appendChild(document, item, "idCategoria", insumo.getIdCategoria());
            root.appendChild(item);
        }
        writeDocument(document, this.dataDir.resolve("insumos.xml"));
    }

    private List<Obra> loadObrasXml() {
        List<Obra> obras = new ArrayList<>();
        Document document = loadDocument(this.dataDir.resolve("obras.xml"));
        if (document == null) {
            return obras;
        }
        NodeList nodeList = document.getElementsByTagName("obra");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            obras.add(new Obra(getChildText(element, "id"), getChildText(element, "nombre"), getChildText(element, "descripcion")));
        }
        return obras;
    }

    private void saveObrasXml(List<Obra> obras) {
        Document document = newDocument();
        Element root = document.createElement("obras");
        document.appendChild(root);
        for (Obra obra : obras) {
            Element item = document.createElement("obra");
            appendChild(document, item, "id", obra.getId());
            appendChild(document, item, "nombre", obra.getNombre());
            appendChild(document, item, "descripcion", obra.getDescripcion());
            root.appendChild(item);
        }
        writeDocument(document, this.dataDir.resolve("obras.xml"));
    }

    private List<String> readLines(Path path) {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeLines(Path path, List<String> lines) {
        try {
            Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Document newDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.newDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Document loadDocument(Path path) {
        if (!Files.exists(path)) {
            return null;
        }
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(path.toFile());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeDocument(Document document, Path path) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(path.toFile()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void appendChild(Document document, Element parent, String tag, String value) {
        Element element = document.createElement(tag);
        element.setTextContent(value == null ? "" : value);
        parent.appendChild(element);
    }

    private String getChildText(Element parent, String tag) {
        NodeList nodeList = parent.getElementsByTagName(tag);
        if (nodeList.getLength() == 0) {
            return "";
        }
        return nodeList.item(0).getTextContent();
    }
}
