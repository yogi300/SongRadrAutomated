package com.songtradr.metadata.pojo;

import javax.xml.bind.annotation.XmlRootElement;

 

@XmlRootElement(name="METADATA")
public class METADATA
{
private Product product;

public Product getProduct ()
{
    return product;
}

public void setProduct (Product product)
{
    this.product = product;
}

@Override
public String toString()
{
    return "ClassPojo [product = "+product+"]";
}
}
		
		

