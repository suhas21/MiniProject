package com.example.suhas.mini1

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    val cmm=ConnectivityHelper()
    lateinit var pbar: ProgressDialog
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mAuth=FirebaseAuth.getInstance()
        pbar= ProgressDialog(this)
        pbar.setMessage("Loading")
        pbar.setCancelable(false)
        if (cmm.isConnectingToInternet(this@SignUpActivity)) {
        } else {
            var sbar= Snackbar.make(signuplayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
        }
    }
    fun signup(v: View) {
        val e = email1.text.toString()
        val p = password1.text.toString()
        val n = input_name.text.toString()
        if (TextUtils.isEmpty(e)) {
            email1.error = "Enter a valid Email Address"
        } else if (TextUtils.isEmpty(p) || p.length <6 || p.length >=14) {
            password1.error = "Password length must between 6 and 14 characters"
        } else if (TextUtils.isEmpty(n)) {
            input_name.error = "Enter a valid Name"
        } else {
            pbar.show()
            Handler().postDelayed({pbar.dismiss()},2000)
            mAuth.createUserWithEmailAndPassword(e, p)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if (task.isSuccessful()) {
                            Toast.makeText(this@SignUpActivity, "Registration Success", Toast.LENGTH_SHORT).show()
                            val u = Intent(this@SignUpActivity, LoginActivity::class.java)
                            startActivity(u)
                        }
                    else {
                            if (cmm.isConnectingToInternet(this@SignUpActivity)) {
                                var sbar= Snackbar.make(signuplayout,"Failed In Getting Details", Snackbar.LENGTH_LONG).show()
                            } else {
                                var sbar= Snackbar.make(signuplayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
                            }
                          }
                    }
                })
        }
    }
    private fun updateUI(user: FirebaseUser?) {
    }
    fun changeLogin(v:View)
    {
        var i=Intent(this,LoginActivity::class.java)
        startActivity(i);
    }
}