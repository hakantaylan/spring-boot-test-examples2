/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2020-2021 the original author or authors.
 */

package football.repository;

import football.entity.Player;
import org.junit.jupiter.api.Test;
import org.quickperf.spring.sql.QuickPerfSqlConfig;
import org.quickperf.sql.annotation.ExpectSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(QuickPerfSqlConfig.class)
@DataJpaTest(properties = {"spring.sql.init.data-locations=classpath:import-teams.sql,classpath:import-players.sql"})
public class PlayerRepositorySelectTest {

    @Autowired
    private PlayerRepository playerRepository;

    @ExpectSelect(1)
    @Test
    public void should_find_all_players() {
        List<Player> players = playerRepository.findAll();
        assertThat(players).hasSize(2);
    }

}
