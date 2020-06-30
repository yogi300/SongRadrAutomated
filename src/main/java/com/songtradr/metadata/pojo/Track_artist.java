package com.songtradr.metadata.pojo;


public class Track_artist
{
private String show_as_composer;

private String show_as_artist;

private String show_as_songwriter;

public String getShow_as_composer ()
{
return show_as_composer;
}

public void setShow_as_composer (String show_as_composer)
{
this.show_as_composer = show_as_composer;
}

public String getShow_as_artist ()
{
return show_as_artist;
}

public void setShow_as_artist (String show_as_artist)
{
this.show_as_artist = show_as_artist;
}

public String getShow_as_songwriter ()
{
return show_as_songwriter;
}

public void setShow_as_songwriter (String show_as_songwriter)
{
this.show_as_songwriter = show_as_songwriter;
}

@Override
public String toString()
{
return "ClassPojo [show_as_composer = "+show_as_composer+", show_as_artist = "+show_as_artist+", show_as_songwriter = "+show_as_songwriter+"]";
}
}