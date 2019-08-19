package com.example.suhas.mini1


import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent
import android.os.Handler
import android.support.design.widget.Snackbar
import android.text.TextUtils
class LoginActivity : AppCompatActivity() {
    lateinit var mAuth:FirebaseAuth
    lateinit var pbar:ProgressDialog
    val cmm=ConnectivityHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth=FirebaseAuth.getInstance()
        pbar= ProgressDialog(this)
        pbar.setMessage("Loading")
        pbar.setCancelable(false)
        if (cmm.isConnectingToInternet(this@LoginActivity)) {
        } else {
            var sbar= Snackbar.make(idlayout,"Requires Internet Connection",Snackbar.LENGTH_LONG).show()
        }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if(mAuth.currentUser!=null)
        {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }
    fun change(v:View)
    {
        val i=Intent(this@LoginActivity,SignUpActivity::class.java)
        startActivity(i);
    }
    fun signin(v: View) {
            val em = email.text.toString()
            val ps = password.text.toString()
            if (TextUtils.isEmpty(em)) {
                email.error = "Enter a valid email address"
            } else if (ps.isEmpty() || ps.length < 6 || ps.length >= 14) {
                password.error = "Password length must between 6 and 14 characters"
            } else {
                pbar.show()
               Handler().postDelayed({pbar.dismiss()},2000)
                mAuth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show()
                            val i = Intent(this@LoginActivity, MainActivity::class.java);
                            startActivity(i);
                        } else {
                            if (cmm.isConnectingToInternet(this@LoginActivity)) {
                                var sbar= Snackbar.make(idlayout,"Login Failed",Snackbar.LENGTH_LONG).show() }
                            else {
                                var sbar= Snackbar.make(idlayout,"Requires Internet Connection",Snackbar.LENGTH_LONG).show()
                            }

                        }
                    }
            }
    }
}
