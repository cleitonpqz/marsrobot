package com.cleitonpqz.marsrobot.services;

import com.cleitonpqz.marsrobot.entities.Land;

public interface LandManager {
    Land create(int xSize, int ySize);
}
