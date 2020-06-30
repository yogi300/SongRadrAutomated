package com.songtradr.metadata.pojo;


public class Item_file
{
private String usage_type;

private String file_type;

private String sub_file_type;

private String content;

public String getUsage_type ()
{
return usage_type;
}

public void setUsage_type (String usage_type)
{
this.usage_type = usage_type;
}

public String getFile_type ()
{
return file_type;
}

public void setFile_type (String file_type)
{
this.file_type = file_type;
}

public String getSub_file_type ()
{
return sub_file_type;
}

public void setSub_file_type (String sub_file_type)
{
this.sub_file_type = sub_file_type;
}

public String getContent ()
{
return content;
}

public void setContent (String content)
{
this.content = content;
}

@Override
public String toString()
{
return "ClassPojo [usage_type = "+usage_type+", file_type = "+file_type+", sub_file_type = "+sub_file_type+", content = "+content+"]";
}
}
