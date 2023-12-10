package com.zachm.buisness_demo.util;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.List;

public class FilePath {
    private List<String> paths;

    public FilePath() {
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public boolean containsPaths() {
        if(this.paths != null) {
            return true;
        }
        else {
            return false;
        }
    }
}
