/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.bugs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Test;

import example.entities.DemoChild;
import example.entities.DemoId;
import example.entities.DemoParent;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM,
 * using its built-in unit test framework. Although ORMStandaloneTestCase is
 * perfectly acceptable as a reproducer, usage of this class is much preferred.
 * Since we nearly always include a regression test with bug fixes, providing
 * your reproducer using this method simplifies the process.
 *
 * What's even better? Fork hibernate-orm itself, add your test case directly to
 * a module's unit tests, then submit it as a PR!
 */
public class ORMUnitTestCase extends BaseCoreFunctionalTestCase {

    @Override
    protected Class[] getAnnotatedClasses() {
        return new Class[] { DemoId.class, DemoParent.class, DemoChild.class };
    }

    @Override
    protected String[] getMappings() {
        return new String[] {};
    }

    // Add in any settings that are specific to your test. See
    // resources/hibernate.properties for the defaults.
    @Override
    protected void configure(final Configuration configuration) {
        super.configure(configuration);

        configuration.setProperty(AvailableSettings.SHOW_SQL, Boolean.TRUE.toString());
        configuration.setProperty(AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString());
        // configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
    }

    // Add your tests, using standard JUnit.
    @Test
    public void hhh123Test() throws Exception {
        // BaseCoreFunctionalTestCase automatically creates the SessionFactory and
        // provides the Session.
        final Session s = openSession();
        final Transaction tx = s.beginTransaction();
//        s.createQuery();
        final CriteriaBuilder cb = s.getCriteriaBuilder();
        final CriteriaQuery<DemoParent> query = cb.createQuery(DemoParent.class);
        final Root<DemoParent> root = query.from(DemoParent.class);
        final Subquery<DemoChild> subquery = query.subquery(DemoChild.class);
        final Root<DemoChild> sqRoot = subquery.from(DemoChild.class);
        subquery.select(sqRoot).where(sqRoot.get("date").isNull());
        query.select(root).where(cb.exists(subquery));
        s.createQuery(query).getResultList();
        // Do stuff...
        tx.commit();
        s.close();
    }
}
