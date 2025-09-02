package com.example.user2.classes.arrow;

import android.graphics.PointF;

import java.util.HashMap;
import java.util.Map;

public class ArrowManager {
    private Map<String, Position> positions;

    public ArrowManager() {
        positions = new HashMap<>();
        initializePositions();
    }

    // 初始化所有位置信息
    private void initializePositions() {
        Map<String, PointF> positionInfo = PositionInfoProvider.getPositionInfo();
        for (Map.Entry<String, PointF> entry : positionInfo.entrySet()) {
            positions.put(entry.getKey(), new Position((int) entry.getValue().x, (int) entry.getValue().y));
        }
    }

    // 添加位置
    public void addPosition(String name, int x, int y) {
        positions.put(name, new Position(x, y));
    }

    // 获取位置
    public Position getPosition(String name) {
        return positions.get(name);
    }

    // 位置类
    public static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
