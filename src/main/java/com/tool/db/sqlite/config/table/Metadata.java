package com.tool.db.sqlite.config.table;


import com.tool.db.sqlite.SqliteSession;
import com.tool.db.sqlite.config.anno.SqliteColumn;
import com.tool.db.sqlite.config.anno.SqliteTable;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * -----------------------------------------------------------------------------
 * NAME    : Metadata
 * DESC    :
 * VER.    : 1.0
 * AUTHOR  : kjcho
 * PROJECT : HYUNDAI EVERDIGM PLM Project
 * <p>
 * Copyright 2021 by ENOVIA All rights reserved.
 * ------------------------------------------------------------------------------
 * Revision history
 * ------------------------------------------------------------------------------
 * DATE            Author                Description
 * ---------------   -----------------   ---------------------------
 * 2021. 10. 29        kjcho               First Creation
 * ------------------------------------------------------------------------------
 */
@SqliteTable(name = "metadata")
public class Metadata
{
    @SqliteColumn(primaryKey = true, notNull = true)
    private String localFileName;
    @SqliteColumn(notNull = true)
    private String name;
    @SqliteColumn(notNull = true)
    private String revision;
    @SqliteColumn(notNull = true)
    private String objectId;
    @SqliteColumn(notNull = true)
    private String format;
    @SqliteColumn(notNull = true)
    private String plmFileName;
    @SqliteColumn(notNull = true)
    private long   downloadTime;
    @SqliteColumn
    private long   lastAccessTime;

    public Metadata() {}

    public Metadata(String localFileName, String name, String revision, String objectId, String format, String plmFileName)
    {
        this.localFileName = localFileName;
        this.name = name;
        this.revision = revision;
        this.objectId = objectId;
        this.format = format;
        this.plmFileName = plmFileName;
    }

    public String getLocalFileName()
    {
        return localFileName;
    }

    public String getName()
    {
        return name;
    }

    public String getRevision()
    {
        return revision;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public String getFormat()
    {
        return format;
    }

    public String getPlmFileName()
    {
        return plmFileName;
    }

    public long getLastAccessTime()
    {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime)
    {
        this.lastAccessTime = lastAccessTime;
    }

    private static SqliteSession    session = SqliteSession.getInstance();
    private static SimpleDateFormat sdf     = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void insertIfNotExists(Metadata metadata) throws SQLException
    {
        if (select(metadata.localFileName) != null)
            updateDownloadTime(metadata.localFileName);
        else
            insert(metadata);
    }


    public static void insert(Metadata metadata) throws SQLException
    {
        Connection        conn = null;
        PreparedStatement stmt = null;
        try
        {
            conn = session.getConnection();
            stmt = conn.prepareStatement("INSERT INTO metadata VALUES(?,?,?,?,?,?,?,null)");
            stmt.setString(1, metadata.localFileName);
            stmt.setString(2, metadata.name);
            stmt.setString(3, metadata.revision);
            stmt.setString(4, metadata.objectId);
            stmt.setString(5, metadata.format);
            stmt.setString(6, metadata.plmFileName);
            stmt.setString(7, sdf.format(new Date()));
            stmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (stmt != null && !stmt.isClosed()) stmt.close();
            if (conn != null && !conn.isClosed()) conn.close();
        }
    }


    public static Metadata select(String localFileName) throws SQLException
    {
        Connection        conn = null;
        PreparedStatement stmt = null;
        ResultSet         rs   = null;
        try
        {
            conn = session.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM metadata where localFileName = ?");
            stmt.setString(1, localFileName);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                return new Metadata(rs.getString("localFileName"),
                                    rs.getString("name"),
                                    rs.getString("revision"),
                                    rs.getString("objectId"),
                                    rs.getString("format"),
                                    rs.getString("plmFileName"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (rs != null && !rs.isClosed()) rs.close();
            if (stmt != null && !stmt.isClosed()) stmt.close();
            if (conn != null && !conn.isClosed()) conn.close();
        }
        return null;
    }


    public static void updateDownloadTime(String localFileName) throws SQLException
    {
        Connection        conn = null;
        PreparedStatement stmt = null;
        ResultSet         rs   = null;
        try
        {
            conn = session.getConnection();
            stmt = conn.prepareStatement("UPDATE metadata SET downloadTime = ? WHERE localFileName = ?");
            stmt.setString(1, sdf.format(new Date()));
            stmt.setString(2, localFileName);
            stmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (rs != null && !rs.isClosed()) rs.close();
            if (stmt != null && !stmt.isClosed()) stmt.close();
            if (conn != null && !conn.isClosed()) conn.close();
        }
    }


    public static void updateLastAccessTime(String localFileName, Date sysdate) throws SQLException
    {
        Connection        conn = null;
        PreparedStatement stmt = null;
        ResultSet         rs   = null;
        try
        {
            conn = session.getConnection();
            stmt = conn.prepareStatement("UPDATE metadata SET lastAccessTime = ? WHERE localFileName = ?");
            stmt.setString(1, sdf.format(sysdate));
            stmt.setString(2, localFileName);
            stmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (rs != null && !rs.isClosed()) rs.close();
            if (stmt != null && !stmt.isClosed()) stmt.close();
            if (conn != null && !conn.isClosed()) conn.close();
        }
    }


}
