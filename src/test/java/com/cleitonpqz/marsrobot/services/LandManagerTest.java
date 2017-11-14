package com.cleitonpqz.marsrobot.services;


import com.cleitonpqz.marsrobot.entities.Land;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LandManagerTest {

    LandManager service;

    @BeforeAll
    public void setUp() throws Exception {
        //GIVEN
        service = (x, y) -> new Land(0, --x, 0, --y);
    }

    @Test
    void create() {
        //WHEN
        Land land = service.create(5, 5);

        //THEN
        assertEquals(0, land.getStartX());
        assertAll("sizes",
                () -> assertEquals(0, land.getStartX()),
                () -> assertEquals(4, land.getEndX()),
                () -> assertEquals(0, land.getStartY()),
                () -> assertEquals(4, land.getEndY())
        );
    }

}