package de.dewarim.goblin;

import de.dewarim.goblin.pc.crafting.Product;
import de.dewarim.goblin.pc.crafting.ProductionJob;

public interface IProductionScript {
    
    void execute(Product product, ProductionJob productionJob);
    
}