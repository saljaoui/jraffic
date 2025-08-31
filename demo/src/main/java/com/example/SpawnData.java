package com.example;

import javafx.scene.input.KeyCode;
import java.util.Objects;

public class SpawnData {
    private final double x;
    private final double y;
    private final KeyCode keyCode;
    
    public SpawnData(double x, double y, KeyCode keyCode) {
        this.x = x;
        this.y = y;
        this.keyCode = keyCode;
    }
    
    public double x() {
        return x;
    }
    
    public double y() {
        return y;
    }
    
    public KeyCode keyCode() {
        return keyCode;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SpawnData spawnData = (SpawnData) obj;
        return Double.compare(spawnData.x, x) == 0 &&
               Double.compare(spawnData.y, y) == 0 &&
               Objects.equals(keyCode, spawnData.keyCode);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x, y, keyCode);
    }
    
    @Override
    public String toString() {
        return "SpawnData{" +
                "x=" + x +
                ", y=" + y +
                ", keyCode=" + keyCode +
                '}';
    }
}