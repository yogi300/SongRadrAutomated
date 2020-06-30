package com.songtradr.metadata.pojo;


public class Release
{
private Offers offers;

private String release_date;

private String permission;

private String territory;

public Offers getOffers ()
{
return offers;
}

public void setOffers (Offers offers)
{
this.offers = offers;
}

public String getRelease_date ()
{
return release_date;
}

public void setRelease_date (String release_date)
{
this.release_date = release_date;
}

public String getPermission ()
{
return permission;
}

public void setPermission (String permission)
{
this.permission = permission;
}

public String getTerritory ()
{
return territory;
}

public void setTerritory (String territory)
{
this.territory = territory;
}

@Override
public String toString()
{
return "ClassPojo [offers = "+offers+", release_date = "+release_date+", permission = "+permission+", territory = "+territory+"]";
}
}
