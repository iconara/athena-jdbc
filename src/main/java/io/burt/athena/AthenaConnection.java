package io.burt.athena;

import software.amazon.awssdk.services.athena.AthenaClient;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class AthenaConnection implements Connection {
    private final ConnectionConfiguration configuration;

    private AthenaClient athenaClient;
    private boolean open;

    public AthenaConnection(AthenaClient athenaClient, ConnectionConfiguration configuration) {
        this.athenaClient = athenaClient;
        this.configuration = configuration;
        this.open = true;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return new AthenaStatement(athenaClient, configuration, PollingStrategies::simpleInterval);
    }

    @Override
    public void close() throws SQLException {
        athenaClient = null;
        open = false;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return !open;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return open;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (isWrapperFor(iface)) {
            return (T) this;
        } else {
            throw new SQLException(String.format("%s is not a wrapper for %s", this.getClass().getName(), iface.getName()));
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isAssignableFrom(getClass());
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        throw new SQLFeatureNotSupportedException("Athena does not support prepared statements");
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new SQLFeatureNotSupportedException("Athena does not have stored procedures");
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return sql;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return true;
    }

    @Override
    public void commit() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void rollback() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return true;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String getCatalog() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return Connection.TRANSACTION_NONE;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void clearWarnings() throws SQLException {
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Clob createClob() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Blob createBlob() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public NClob createNClob() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String getSchema() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }
}