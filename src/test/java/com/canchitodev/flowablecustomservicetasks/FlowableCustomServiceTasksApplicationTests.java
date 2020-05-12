package com.canchitodev.flowablecustomservicetasks;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.test.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class FlowableCustomServiceTasksApplicationTests {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private HistoryService historyService;

	@Test
	@Deployment(resources = "processes/my-first-custom-service-task.bpmn20.bpmn")
	void myFirstCustomServiceTaskTest() {
		// Start a new process instance
		ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey("myFirstCustomServiceTask");

		// Make sure the process has ended
		await().atMost(30L, TimeUnit.SECONDS).until(
				() -> this.historyService.createHistoricProcessInstanceQuery()
						.processInstanceId(processInstance.getProcessInstanceId())
						.finished()
						.singleResult() != null
		);
	}

	@Test
	@Deployment(resources = "processes/my-first-field-injection.bpmn20.bpmn")
	void myFirstFieldInjectionTest() {
		// Start a new process instance
		ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey("myFirstFieldInjection");

		// Make sure the process has ended
		await().atMost(30L, TimeUnit.SECONDS).until(
				() -> this.historyService.createHistoricProcessInstanceQuery()
						.processInstanceId(processInstance.getProcessInstanceId())
						.finished()
						.singleResult() != null
		);
	}
}