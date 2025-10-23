import { useEffect, useState } from "react";
import TaskService from "../api/TaskService";
import "./TaskCard.css";

export default function TaskBoard() {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    loadTasks();
  }, []);

  const loadTasks = async () => {
    try {
      const res = await TaskService.getAllTasks();
      setTasks(res.data || []);
    } catch (err) {
      console.error("Error loading tasks:", err);
    }
  };

  const updateStatus = async (task, newStatus) => {
    try {
      const updatedTask = { ...task, status: newStatus };
      await TaskService.updateTask(task.id, updatedTask); 
      loadTasks();
    } catch (err) {
      console.error("Failed to update task status:", err);
    }
  };

  const statuses = ["ASSIGNED", "PROGRESS", "COMPLETED", "REJECTED"];

  return (
    <div className="board-container">
      <h2 className="board-title">Task Board</h2>
      <div className="board">
        {statuses.map((status) => (
          <div key={status} className="column">
            <h3>{status}</h3>

            {tasks
              .filter((task) => task.status === status)
              .map((task) => (
                <div key={task.id} className="task-card">
                  <h4>{task.title}</h4>
                  <p>{task.description}</p>
                  <p><b>Priority:</b> {task.priority}</p>
                  <p><b>Start:</b> {task.startDate}</p>
                  <p><b>End:</b> {task.endDate}</p>

                  <div className="card-actions">
                    {status === "ASSIGNED" && (
                      <button onClick={() => updateStatus(task, "PROGRESS")}>Start</button>
                    )}

                    {status === "PROGRESS" && (
                      <>
                        <button onClick={() => updateStatus(task, "COMPLETED")}>Complete</button>
                        <button onClick={() => updateStatus(task, "REJECTED")}>Reject</button>
                      </>
                    )}

                    {status === "COMPLETED" && (
                      <button onClick={() => updateStatus(task, "PROGRESS")}>Restart</button>
                    )}

                    {status === "REJECTED" && (
                      <button onClick={() => updateStatus(task, "ASSIGNED")}>Reassign</button>
                    )}
                  </div>
                </div>
              ))}
          </div>
        ))}
      </div>
    </div>
  );
}
