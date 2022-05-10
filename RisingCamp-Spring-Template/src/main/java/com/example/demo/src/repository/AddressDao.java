package com.example.demo.src.repository;

import com.example.demo.src.domain.dto.address.PatchAddressReq;
import com.example.demo.src.domain.dto.address.PostAddressReq;
import com.example.demo.src.domain.entitiy.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Slf4j
public class AddressDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 주소 조회
    public List<Address> getAddress(Long userIdx) {
        String getAddressQuery = "select * from Address A join TimeInfo TI on A.addressIdx = TI.addressIdx where A.userIdx = ? AND A.status = true AND TI.status = true";
        return this.jdbcTemplate.query(getAddressQuery,
                (rs, rowNum) -> new Address(
                        rs.getLong("A.addressIdx"),
                        rs.getString("A.name"),
                        rs.getString("A.phoneNumber"),
                        rs.getString("A.city"),
                        rs.getString("A.street"),
                        rs.getString("A.detail"),
                        rs.getString("A.zipcode"),
                        rs.getString("TI.basicTimeInfo"),
                        rs.getString("TI.basicHousePassword"),
                        rs.getString("TI.dawnTimeInfo"),
                        rs.getString("TI.dawnTimePassword"),
                        rs.getBoolean("A.defaultAddress")
                ),
                userIdx
        );
    }

    public int updateAddress(PatchAddressReq getAddressReq) {
        String updateAddressQuery = "update Address A" +
                " join TimeInfo TI on A.addressIdx = TI.addressIdx" +
                " set A.name = ?," +
                " A.city = ?," +
                " A.street = ?," +
                " A.detail = ?," +
                " A.zipcode = ?," +
                " A.phoneNumber = ?," +
                " TI.basicTimeInfo = ?," +
                " TI.basicHousePasswozrd = ?," +
                " TI.dawnTimeInfo = ?," +
                " TI.dawnTimePassword = ?," +
                " A.defaultAddress = ?" +
                " where A.addressIdx=? AND A.status = true AND TI.status = true";

        Object[] updateAddressParams = new Object[]{
                getAddressReq.getName(),
                getAddressReq.getCity(),
                getAddressReq.getStreet(),
                getAddressReq.getDetail(),
                getAddressReq.getZipcode(),
                getAddressReq.getPhoneNumber(),
                getAddressReq.getBasicTimeInfo(),
                getAddressReq.getBasicHousePassword(),
                getAddressReq.getDawnTimeInfo(),
                getAddressReq.getDawnTimePassword(),
                getAddressReq.getIsDefault(),
                getAddressReq.getAddressIdx()
        };

        return this.jdbcTemplate.update(updateAddressQuery, updateAddressParams);

    }

    public int insertAddress(Long userIdx, PostAddressReq postAddressReq) {
        String insertAddressQuery = "insert into Address(userIdx, name, phoneNumber, city, street, detail, zipcode, defaultAddress)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] insertAddressParams = {userIdx,
                postAddressReq.getName(),
                postAddressReq.getPhoneNumber(),
                postAddressReq.getCity(),
                postAddressReq.getStreet(),
                postAddressReq.getDetail(),
                postAddressReq.getZipcode(),
                postAddressReq.getIsDefault()
        };
        return this.jdbcTemplate.update(insertAddressQuery, insertAddressParams);
    }

    public int deleteAddress(int addressIdx) {
        String deleteAddressQuery = "update Address set status = 0 where addressIdx =?";
        return this.jdbcTemplate.update(deleteAddressQuery, addressIdx);
    }

    public int initDefaultAddress(Long userIdx) {
        String initDefaultAddressQuery = "update Address A join User U set A.defaultAddress = false where A.status = true AND U.status = true AND A.userIdx = ?";
        return this.jdbcTemplate.update(initDefaultAddressQuery, userIdx);
    }

    public int insertTimeInfo(int addressIdx, PostAddressReq postAddressReq) {
        String insertTimeInfoQuery = "insert into TimeInfo(addressIdx, basicTimeInfo, basicHousePassword, dawnTimeInfo, dawnTimePassword)" +
                " VALUES (?, ?, ?, ?, ?)";
        Object[] insertTimeInfoParams = {
                addressIdx,
                postAddressReq.getBasicTimeInfo(),
                postAddressReq.getBasicHousePassword(),
                postAddressReq.getDawnTimeInfo(),
                postAddressReq.getDawnTimePassword(),
        };
        return this.jdbcTemplate.update(insertTimeInfoQuery, insertTimeInfoParams);
    }
}


