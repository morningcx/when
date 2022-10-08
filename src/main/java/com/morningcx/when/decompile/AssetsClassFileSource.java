package com.morningcx.when.decompile;

import org.benf.cfr.reader.api.ClassFileSource;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;

import javax.tools.JavaFileObject;
import java.util.Collection;
import java.util.Map;

/**
 * AssetsClassFileSource
 *
 * @author gcx
 * @date 2022/6/1
 */
public class AssetsClassFileSource implements ClassFileSource {

    private final Map<String, byte[]> classes;

    public AssetsClassFileSource(Map<String, byte[]> classes) {
        this.classes = classes;
    }

    @Override
    public void informAnalysisRelativePathDetail(String usePath, String classFilePath) {

    }

    @Override
    public Collection<String> addJar(String jarPath) {
        return null;
    }

    @Override
    public String getPossiblyRenamedPath(String path) {
        return path;
    }

    @Override
    public Pair<byte[], String> getClassFileContent(String path) {
        String name = path.replace('/', '.').replace(JavaFileObject.Kind.CLASS.extension, "");
        byte[] bytes = classes.get(name);
        if (bytes == null) {
            throw new RuntimeException("class not find " + name);
        }
        return Pair.make(bytes, path);

    }
}
