import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import LoginForm from "../features/Authentication/LoginForm";
import Logo from "../ui/Logo";
import useAuth from "../hooks/useAuth";

function Login() {
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/", { replace: true });
    }
  }, [isAuthenticated, navigate]);

  return (
    <section className="bg-gray-50 min-h-screen flex items-center justify-center">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto w-full max-w-md">
        <div className="mb-6 flex items-center justify-center">
          <Logo />
        </div>

        <div className="w-full bg-white rounded-lg shadow-lg shadow-gray-300 border-gray-200 border md:mt-0 sm:max-w-md xl:p-0 ">
          <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl text-center">
              Log In
            </h1>
            <LoginForm />
          </div>
        </div>
      </div>
    </section>
  );
}

export default Login;
