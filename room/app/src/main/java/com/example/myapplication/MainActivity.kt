package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myDao: MyDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDao = MyDatabase.getDatabase(this).getMyDao()

        CoroutineScope(Dispatchers.IO).launch {
            with(myDao) {
                insertStudent(Student(1, "james"))
                insertStudent(Student(2, "john"))
                insertClass(ClassInfo(1, "c-lang", "Mon 9:00", "E301", 1))
                insertClass(ClassInfo(2, "android prog", "Tue 9:00", "E302", 1))
                insertEnrollment(Enrollment(1, 1))
                insertEnrollment(Enrollment(1, 2))
            }
        }

        val allStudents = myDao.getAllStudent()
        allStudents.observe(this) {
            val str = StringBuilder().apply {
                for ((id, name) in it) {
                    append(id)
                    append("-")
                    append(name)
                    append("\n")
                }
            }.toString()
            binding.textStudentList.text = str
        }

        binding.queryStudent.setOnClickListener {
            val id = binding.editStudentId.text.toString().toInt()
            CoroutineScope(Dispatchers.IO).launch {
                val results = myDao.getStudentsWithEnrollment(id) // 리턴타입 : List<StudentWithEnrollment>
                if (results.isNotEmpty()) {
                    val str = StringBuilder().apply {
                        append(results[0].student.id)
                        append("-")
                        append(results[0].student.name)
                        append(":")
                        for (c in results[0].enrollments) {
                            append(c.cid)
                            val cls_result = myDao.getClassInfo(c.cid)
                            if (cls_result.isNotEmpty())
                                append("(${cls_result[0].name})")
                            append(",")
                        }
                    }
                    withContext(Dispatchers.Main) {
                        binding.textQueryStudent.text = str
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.textQueryStudent.text = ""
                    }
                }
            }
        }

        binding.addStudent.setOnClickListener {
            val id = binding.editStudentId.text.toString().toInt()
            val name = binding.editStudentName.text.toString()
            if (id > 0 && name.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    myDao.insertStudent(Student(id, name))
                }
            }
        }

        binding.deleteStudent.setOnClickListener {
            val id = binding.editStudentId.text.toString().toInt()

            runBlocking {
                val del_student = myDao.getStudentsWithEnrollment(id).get(0).student
                val en = myDao.getStudentsWithEnrollment(id).get(0).enrollments
                for (c in en) {
                    myDao.deleteEnrollment(c)
                }



                myDao.deleteStudent(del_student)
            }
        }

        binding.enrollment.setOnClickListener {
            val id = binding.editStudentId.text.toString().toInt()
            val random = Random()
            val randNum = random.nextInt(2) + 1

            runBlocking {
                myDao.insertEnrollment(Enrollment(id, randNum))
            }
        }
    }
}