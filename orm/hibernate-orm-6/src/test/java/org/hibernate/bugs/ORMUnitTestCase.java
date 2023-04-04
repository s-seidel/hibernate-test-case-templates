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

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Test;

import demo.Employee;
import demo.Skill;
import demo.SkillAssignment;
import demo.SkillAssignmentId;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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

    // Add your entities here.
    @Override
    protected Class[] getAnnotatedClasses() {
        return new Class[] { Employee.class, SkillAssignment.class, SkillAssignmentId.class, Skill.class };
    }

    // If you use *.hbm.xml mappings, instead of annotations, add the mappings here.
    @Override
    protected String[] getMappings() {
        return new String[] {
//				"Foo.hbm.xml",
//				"Bar.hbm.xml"
        };
    }

    // If those mappings reside somewhere other than resources/org/hibernate/test,
    // change this.
    @Override
    protected String getBaseForMappings() {
        return "org/hibernate/test/";
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
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
        final Root<Employee> root = cr.from(Employee.class);
        cr.select(root);

        final EntityGraph<Employee> eg = s.createEntityGraph(Employee.class);
        eg.addSubgraph("currentSkills").addAttributeNodes("skill");

        final Query<Employee> query = session.createQuery(cr).setHint("javax.persistence.loadgraph", eg);

        // the resulting query:
        //
        // select
        // e1_0."id",
        // c1_0."employeeid",
        // c1_0."skillid",
        // s1_0."id",
        // c1_0."end",
        // c1_0."start"
        // from
        // "employee" e1_0
        // left join
        // "skillassignment" c1_0
        // on e1_0."id"=c1_0."employeeid"
        // and (
        // c1_0."start" <= cast(current_timestamp as date)
        // and (
        // c1_0."end" >= cast(current_timestamp as date)
        // or c1_0."end" is null
        // )
        // )
        // and (
        // c1_0."start" <= cast(current_timestamp as date)
        // and (
        // c1_0."end" >= cast(current_timestamp as date)
        // or c1_0."end" is null
        // )
        // )
        // join
        // "skill" s1_0
        // on s1_0."id"=c1_0."skillid"

        final List<Employee> results = query.getResultList();

        // the expected query:
        //
        // select
        // e1_0."id",
        // c1_0."employeeid",
        // c1_0."skillid",
        // s1_0."id",
        // c1_0."end",
        // c1_0."start"
        // from
        // "employee" e1_0
        // left join
        // "skillassignment" c1_0
        // on e1_0."id"=c1_0."employeeid"
        // and (
        // c1_0."start" <= cast(current_timestamp as date)
        // and (
        // c1_0."end" >= cast(current_timestamp as date)
        // or c1_0."end" is null
        // )
        // )
        // left join
        // "skill" s1_0
        // on s1_0."id"=c1_0."skillid"

        tx.commit();
        s.close();
    }
}
