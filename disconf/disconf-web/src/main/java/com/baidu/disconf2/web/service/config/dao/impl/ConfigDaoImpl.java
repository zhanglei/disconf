package com.baidu.disconf2.web.service.config.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baidu.disconf2.core.common.constants.DisConfigTypeEnum;
import com.baidu.disconf2.web.service.config.bo.Config;
import com.baidu.disconf2.web.service.config.dao.ConfigDao;
import com.baidu.dsp.common.dao.AbstractDao;
import com.baidu.dsp.common.dao.Columns;
import com.baidu.dsp.common.form.RequestListBase.Page;
import com.baidu.dsp.common.utils.DaoUtils;
import com.baidu.ub.common.generic.dao.operator.DaoPage;
import com.baidu.ub.common.generic.dao.operator.Match;
import com.baidu.ub.common.generic.vo.DaoPageResult;

/**
 * 
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Service
public class ConfigDaoImpl extends AbstractDao<Long, Config> implements
        ConfigDao {

    /**
     * 
     */
    @Override
    public Config getByParameter(Long appId, Long envId, String version,
            String key, DisConfigTypeEnum disConfigTypeEnum) {

        return findOne(new Match(Columns.APP_ID, appId), new Match(
                Columns.ENV_ID, envId), new Match(Columns.VERSION, version),
                new Match(Columns.TYPE, disConfigTypeEnum.getType()),
                new Match(Columns.NAME, key));
    }

    @Override
    public List<Config> getConfByAppId(Long appId) {

        return find(new Match(Columns.APP_ID, appId));

    }

    /**
     * 
     */
    @Override
    public DaoPageResult<Config> getConfigList(Long appId, Long envId,
            String version, Page page) {

        DaoPage daoPage = DaoUtils.daoPageAdapter(page);
        List<Match> matchs = new ArrayList<Match>();
        if (appId != null) {
            matchs.add(new Match(Columns.APP_ID, appId));
        }
        if (envId != null) {
            matchs.add(new Match(Columns.ENV_ID, envId));
        }
        if (version != null) {
            matchs.add(new Match(Columns.VERSION, version));
        }

        return page2(matchs, daoPage);
    }

    /**
     * 
     */
    @Override
    public void updateValue(Long configId, String value) {

        update(modify(Columns.VALUE, value), match(Columns.CONFIG_ID, configId));
    }

    @Override
    public String getValue(Long configId) {
        Config config = get(configId);
        return config.getValue();
    }
}