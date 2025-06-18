import '@/styles/utilities.css';

import { Card, CardAction, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from "@/app/domains/shared/components/card";
import { Input } from "@/app/domains/shared/components/input.js";
import { Button } from "@/app/domains/shared/components/button.js";
import { Label } from "@/app/domains/shared/components/label.js";
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { createLogInSchema } from "../schemas/login";
import type { LogInFormData } from "../schemas/login";
import { logInUser } from '../application/authService';
import { useNavigate } from 'react-router-dom';
import { useUser } from "@/app/contexts/UserContext";
import { toast } from "sonner"

export function LoginForm() {
    const { t } = useTranslation();
    const schema = createLogInSchema(t);
    const navigate = useNavigate();
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm<LogInFormData>({
        resolver: zodResolver(schema),
    });
    // Importing the setUser function from the UserContext to save the user data after login
    const { setUser } = useUser();
    
    const onSubmit = async (data: LogInFormData) => {
    try {
        const requestBody: LogInFormData = {
            ...data,
            purpose: "loginUser",
        };
        const result = await logInUser(requestBody);
        // Saving the user data in the context
        setUser(result);

        // After successful login, redirect to the authUser page
        navigate('/login/authUser');
    } catch (error) {
        toast.error(t('login.error'));
    }
    };

    return (
        <div className="form-background">
            <h1 className="card-title">{t('app.title')}</h1>
            <Card className="form-card">
                <CardHeader>
                    <CardTitle className="text-2xl">{t('login.title')}</CardTitle>
                    <CardDescription className="card-description">{t('login.welcomeBack')}</CardDescription>
                    <CardAction>
                        <Link to="/" className="text-link">{t('login.signUp')}</Link>
                    </CardAction>
                </CardHeader>
                <CardContent className="grid gap-4">
                    <form onSubmit={handleSubmit(onSubmit)} className="grid gap-4 md:gap-6">
                        <div className="grid gap-2">
                            <Label htmlFor="email">{t('user.email')}</Label>
                            <Input id="email" type="email" {...register("email")} className="input-dark"/>
                            {errors.email && <p className="text-red-500 text-sm">{errors.email.message}</p>}
                        </div>
                        <div className="grid gap-2">
                            <div className="flex items-center">
                                <Label htmlFor="password">{t('user.password')}</Label>
                                <Link to="/forgot-password" className="ml-auto text-sm text-blue-400 hover:underline">{t('login.forgotPassword')}</Link>
                            </div>
                            <Input id="password" type="password" {...register("password")} className="input-dark"/>
                            {errors.password && <p className="text-red-500 text-sm">{errors.password.message}</p>}
                        </div>
                        <Button type="submit" className="button-green" disabled={isSubmitting}>
                            {t('login.submit')}
                        </Button>
                    </form>
                </CardContent>
                <CardFooter />
            </Card>
        </div>
    );
}

