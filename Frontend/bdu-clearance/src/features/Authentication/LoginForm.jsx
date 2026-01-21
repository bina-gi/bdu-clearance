import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import toast from "react-hot-toast";
import useAuth from "../../hooks/useAuth";
import Input from "../../ui/Input";
import FormRow from "../../ui/FormRow";

function LoginForm() {
  const [serverError, setServerError] = useState("");
  const { login } = useAuth();
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm({
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const onSubmit = async (data) => {
    setServerError("");
    try {
      await login(data.username, data.password);
      toast.success("Logged in successfully");
      navigate("/", { replace: true });
    } catch (err) {
      const message =
        err.response?.data?.message || err.message || "Failed to login";
      setServerError(message);
      toast.error(message);
    }
  };

  return (
    <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit(onSubmit)}>
      <FormRow label="Username" id="username" error={errors.username?.message}>
        <Input
          type="text"
          id="username"
          placeholder="Enter your username"
          disabled={isSubmitting}
          error={errors.username}
          {...register("username", {
            required: "Username is required",
          })}
        />
      </FormRow>

      <FormRow label="Password" id="password" error={errors.password?.message}>
        <Input
          type="password"
          id="password"
          placeholder="••••••••"
          disabled={isSubmitting}
          error={errors.password}
          {...register("password", {
            required: "Password is required",
            minLength: {
              value: 4,
              message: "Password must be at least 4 characters",
            },
          })}
        />
      </FormRow>

      <button
        type="submit"
        disabled={isSubmitting}
        className={`w-full text-white bg-blue-600 hover:bg-blue-700 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center transition-colors duration-200 ${isSubmitting ? "opacity-70 cursor-not-allowed" : ""}`}
      >
        {isSubmitting ? "Signing in..." : "Sign in"}
      </button>
    </form>
  );
}

export default LoginForm;
