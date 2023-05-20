package com.majid.codinghelper.auth.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.majid.codinghelper.R
import com.majid.codinghelper.auth.authviewmodel.AuthViewModel
import com.majid.codinghelper.auth.models.User
import com.majid.codinghelper.auth.ui.ViewProfileActivity
import com.majid.codinghelper.common.hide
import com.majid.codinghelper.common.show
import com.majid.codinghelper.databinding.FragmentLoginBinding
import com.majid.codinghelper.ui.MainActivity
import com.majid.codinghelper.utils.ViewUtils
import java.util.concurrent.TimeUnit

class LoginFragment() : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var authViewModel: AuthViewModel
    var user = User()

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    var countryCode = ""
    lateinit var cTimer: CountDownTimer
    var phone: String = ""
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

   //facebook
    private lateinit var callbackManager: CallbackManager


    constructor(objects: Array<Any>) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        initViewModel()
        setListeners()
        initGso()
        initPhoneAuth()
        callbackManager = CallbackManager.Factory.create()

        val  accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken!= null && !accessToken.isExpired){
            getFacebookData()
//            authViewModel.setOpenFacebookProfileFragment(arrayOf(1,2))

            // goto Main Activity
//            startActivity(Intent(requireActivity(),ViewProfileActivity::class.java))
//            requireActivity().finish()
        }



        LoginManager.getInstance().registerCallback(callbackManager,
        object :FacebookCallback<LoginResult>{
            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

                // print error
            }

            override fun onSuccess(result: LoginResult) {
                startActivity(Intent(requireActivity(),MainActivity::class.java))
            }

        })


    }

    private fun getFacebookData() {

        val accessToken = AccessToken.getCurrentAccessToken()


    }


    private fun setListeners() {
        binding.btnGoogleSignIn.setOnClickListener {
            signIn()
        }

        binding.layoutPhoneLogin.tvVerify.setOnClickListener {
            countryCode = binding.layoutPhoneLogin.layoutPhone.tvCountryCode.text.toString()
            var mobile = binding.layoutPhoneLogin.layoutPhone.etPhone.text.toString()
            phone = countryCode + mobile
            Log.e("PHONE NUMBER", phone)

            if (isValid()) {
                startPhoneNumberVerification(phone)

            }

        }


        binding.layoutVerifyPin.tvVerify.setOnClickListener {
            if (pinEntered()) {
                var otp = binding.layoutVerifyPin.etPin.text.toString()

                verifyPhoneNumberWithCode(storedVerificationId, otp)
            }
        }

        binding.layoutVerifyPin.tvResendOtp.setOnClickListener {
            binding.layoutVerifyPin.tvTimer.text = ""
            binding.layoutVerifyPin.tvResendOtp.hide()
            resendVerificationCode(phone, resendToken)
        }


        binding.tvFacebookLogin.setOnClickListener {
            facebookLogin()
        }

    }

    private fun facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(requireActivity(), listOf("public_profile,email"))
    }

    /**
     *  For Phone Authentication
     * **/
    private fun initPhoneAuth() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                binding.layoutVerifyPin.tvPhone.text = "Enter verification code  sent to\n ${phone}"
                binding.layoutVerifyPin.root.show()

                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                startTimer()
            }
        }

    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        // [START start_phone_auth]
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

        // [END start_phone_auth]
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {

        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
        Log.d(TAG, credential.smsCode.toString())
        signInWithPhoneAuthCredential(credential)
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential Phone :success")
                    ViewUtils.showToast(
                        requireActivity(),
                        "Verified" + credential.smsCode.toString(),
                    )

                    val userP = task.result?.user
                    user.mobile = userP!!.phoneNumber!!

                    Log.d(TAG, " Mobile : " + user.mobile)
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }


    private fun isValid(): Boolean {

        if (binding.layoutPhoneLogin.layoutPhone.etPhone.text.isEmpty()) {
            binding.layoutPhoneLogin.layoutPhone.etPhone.setError("Enter Number")
            return false
        }
        if (binding.layoutPhoneLogin.layoutPhone.etPhone.text.length < 9) {
            binding.layoutPhoneLogin.layoutPhone.etPhone.setError("Enter Ten Digit Number")
            return false
        }
        return true

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }

    /**
     *  For Google Sign IN
     * **/
    private fun initGso() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        ViewUtils.showProgressDialog(requireActivity(), "Signing In Please Wait..")
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }

    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                ViewUtils.hideProgressDialog()
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential :success")
                    ViewUtils.showToast(requireActivity(), "Sign In Successful")
                    val userF = auth.currentUser

                    user.name = userF!!.displayName!!
                    user.email = userF.email!!
                    user.userId = userF.uid

                    Log.d(TAG, "" + user.email + "\n " + user.name + "\n " + user.userId)
                    updateUI(userF)
                } else {
                    // If sign in fails, display a message to the user.
                    ViewUtils.showToast(requireActivity(), "Sign In Failed")

                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }
    // [END signin]

    private fun updateUI(user: FirebaseUser?) {

    }


    private fun pinEntered(): Boolean {

        if (binding.layoutVerifyPin.etPin.text.length < 6) {
            binding.layoutVerifyPin.etPin.setError("Enter Six Digit otp")
            return false
        }
        if (binding.layoutVerifyPin.etPin.text.toString().isEmpty()) {
            binding.layoutVerifyPin.etPin.setError("Enter Pin")
            return false
        }

        return true
    }


    fun startTimer() {

        cTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.layoutVerifyPin.tvTimer.show()
                binding.layoutVerifyPin.tvResendOtp.hide()
                binding.layoutVerifyPin.tvTimer.setText("resend OtP: " + (millisUntilFinished / 1000).toString())
            }

            override fun onFinish() {
                binding.layoutVerifyPin.tvTimer.text = ""
                binding.layoutVerifyPin.tvTimer.hide()
                binding.layoutVerifyPin.tvResendOtp.show()

            }
        }
        cTimer.start()
    }


    companion object {

        private const val TAG = "GoogleActivity"
        private const val GOOGLE_SIGN_IN = 1313
        private const val FACEBOOK_SIGN_IN = 1308

        @JvmStatic
        fun newInstance() =
            LoginFragment()


        @JvmStatic
        fun newInstance(objects: Array<Any>) =
            LoginFragment(objects)
    }

    private fun initViewModel() {
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
    }


}