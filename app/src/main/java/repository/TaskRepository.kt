package com.example.mytask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytask.data.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TaskRepository {

    private val db = FirebaseFirestore.getInstance()

    private val _allTasks = MutableLiveData<List<Task>>()
    val allTasks: LiveData<List<Task>> get() = _allTasks

    fun getAllTasks() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        db.collection("tasks")
            .document(uid)
            .collection("userTasks")
            .get()
            .addOnSuccessListener { snapshot ->
                val tasks = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Task::class.java)?.copy(id = doc.id)
                }
                _allTasks.postValue(tasks)
            }
    }

    fun getTasksByCategory(category: String): LiveData<List<Task>> {
        val tasksByCategory = MutableLiveData<List<Task>>()
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return tasksByCategory

        db.collection("tasks")
            .document(uid)
            .collection("userTasks")
            .whereEqualTo("category", category)
            .get()
            .addOnSuccessListener { snapshot ->
                val filtered = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Task::class.java)?.copy(id = doc.id)
                }
                tasksByCategory.postValue(filtered)
            }

        return tasksByCategory
    }

    fun insert(task: Task, onComplete: (Boolean) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return onComplete(false)

        db.collection("tasks")
            .document(uid)
            .collection("userTasks")
            .add(task)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun update(taskId: String, updatedTask: Task, onComplete: (Boolean) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return onComplete(false)

        db.collection("tasks")
            .document(uid)
            .collection("userTasks")
            .document(taskId)
            .set(updatedTask)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun delete(taskId: String, onComplete: (Boolean) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return onComplete(false)

        db.collection("tasks")
            .document(uid)
            .collection("userTasks")
            .document(taskId)
            .delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}
