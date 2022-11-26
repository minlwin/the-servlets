package com.jdc.basic.location.model.impl;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.jdc.basic.location.model.DistrictModel;
import com.jdc.basic.location.model.dto.District;
import com.jdc.basic.location.model.form.DistrictForm;

public class DistrictModelImpl implements DistrictModel {

	private DataSource dataSource;

	public DistrictModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public District create(DistrictForm form) {
		
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement("")) {
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return null;
	}

	@Override
	public District update(int id, DistrictForm form) {
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement("")) {
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return null;
	}

	@Override
	public District findById(int id) {
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement("")) {
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return null;
	}

	@Override
	public List<District> search(int stateId, String name) {
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement("")) {
			
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return null;
	}

}
