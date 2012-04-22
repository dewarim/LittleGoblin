package de.dewarim.goblin;

import de.dewarim.goblin.pc.crafting.ProductionJob;
import de.dewarim.goblin.pc.crafting.Product;

public interface IProductionScript {

	void execute(Product product, ProductionJob productionJob);
}