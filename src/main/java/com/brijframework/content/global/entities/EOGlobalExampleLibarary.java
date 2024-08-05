package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.*;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = EOGLOBAL_EXAMPLE_LIBARARY)
public class EOGlobalExampleLibarary extends EOGlobalItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "exampleLibarary")
	private List<EOGlobalExampleItem> exampleItems;

	public List<EOGlobalExampleItem> getExampleItems() {
		return exampleItems;
	}

	public void setExampleItems(List<EOGlobalExampleItem> exampleItems) {
		this.exampleItems = exampleItems;
	}
	
}
