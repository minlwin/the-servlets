package com.jdc.basic.location.model.impl;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.jdc.basic.location.model.TownshipModel;
import com.jdc.basic.location.model.dto.Township;
import com.jdc.basic.location.model.form.TownshipForm;

public class TownshipModelImpl implements TownshipModel {

	private DataSource dataSource;

	public TownshipModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public Township create(TownshipForm form) {
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement("")) {
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return null;
	}

	@Override
	public Township update(int id, TownshipForm form) {
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement("")) {
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return null;
	}

	@Override
	public Township findById(int id) {
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement("")) {
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return null;
	}

	@Override
	public List<Township> search(int districtId, String name) {
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement("")) {
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return null;
	}

}
