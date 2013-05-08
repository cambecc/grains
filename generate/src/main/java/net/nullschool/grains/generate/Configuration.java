package net.nullschool.grains.generate;

import net.nullschool.collect.ConstSet;
import net.nullschool.collect.basic.BasicConstSet;

import java.nio.charset.Charset;
import java.nio.file.Path;


/**
 * 2013-02-16<p/>
 *
 * @author Cameron Beccario
 */
public final class Configuration {

    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String DEFAULT_LINE_WIDTH = "100";
    public static final String DEFAULT_LINE_SEPARATOR = "\n";
    public static final String DEFAULT_COLLECTION_CLASS = "net.nullschool.collect.ConstCollection";
    public static final String DEFAULT_SET_CLASS = "net.nullschool.collect.ConstSet";
    public static final String DEFAULT_LIST_CLASS = "net.nullschool.collect.ConstList";
    public static final String DEFAULT_MAP_CLASS = "net.nullschool.collect.ConstMap";


    private Charset charset = Charset.forName(DEFAULT_ENCODING);
    private int lineWidth = Integer.parseInt(DEFAULT_LINE_WIDTH);
    private String lineSeparator = DEFAULT_LINE_SEPARATOR;
    private Path output;
    private ConstSet<String> searchPackages = BasicConstSet.emptySet();
    private ClassLoader searchLoader;
    private String collectionClass = DEFAULT_COLLECTION_CLASS;
    private String setClass = DEFAULT_SET_CLASS;
    private String listClass = DEFAULT_LIST_CLASS;
    private String mapClass = DEFAULT_MAP_CLASS;

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public Path getOutput() {
        return output;
    }

    public void setOutput(Path output) {
        this.output = output;
    }

    public ConstSet<String> getSearchPackages() {
        return searchPackages;
    }

    public void setSearchPackages(ConstSet<String> searchPackages) {
        this.searchPackages = searchPackages;
    }

    public ClassLoader getSearchLoader() {
        return searchLoader;
    }

    public void setSearchLoader(ClassLoader searchLoader) {
        this.searchLoader = searchLoader;
    }

    public String getCollectionClass() {
        return collectionClass;
    }

    public void setCollectionClass(String collectionClass) {
        this.collectionClass = collectionClass;
    }

    public String getSetClass() {
        return setClass;
    }

    public void setSetClass(String setClass) {
        this.setClass = setClass;
    }

    public String getListClass() {
        return listClass;
    }

    public void setListClass(String listClass) {
        this.listClass = listClass;
    }

    public String getMapClass() {
        return mapClass;
    }

    public void setMapClass(String mapClass) {
        this.mapClass = mapClass;
    }
}
