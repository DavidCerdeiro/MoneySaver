import '@/styles/utilities.css';

import { Card, CardAction, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from  "@/app/domains/shared/components/card";
import { Input } from "@/app/domains/shared/components/input";
import { Button } from "@/app/domains/shared/components/button";
import { Label } from "@/app/domains/shared/components/label.js";
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import type { LogInFormData} from "@/app/domains/auth/schemas/login";
import { createLogInSchema } from "@/app/domains/auth/schemas/login";
import { logInUser } from '../application/authService';
import { useNavigate } from 'react-router-dom';
import { toast } from "sonner"

export function LoginForm() {
    const { t, i18n } = useTranslation();
    const schema = createLogInSchema(t);
    const navigate = useNavigate();
    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm<LogInFormData>({
        resolver: zodResolver(schema),
    });
    
    const onSubmit = async (data: LogInFormData) => {
        try {
        const languageTag = i18n.language || "en";
            const [language, country] = languageTag.split("-");

            const locale = country ? `${language}_${country.toUpperCase()}` : language;
            data.locale = locale;
            console.log("Locale set to:", data.locale);
            const result = await logInUser(data);

            // Saving the email in order to use it later
            sessionStorage.setItem('email', result.email);

            navigate('/login/authUser');
        } catch (error) {
            toast.error(t('login.error'));
        }
    };

    return (
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
                            <Label htmlFor="email">{t('domains.user.email')}</Label>
                            <Input id="email" type="email" {...register("email")} className="input-form"/>
                            {errors.email && <p className="text-red-500 text-sm">{errors.email.message}</p>}
                        </div>
                        <div className="grid gap-2">
                            <div className="flex items-center">
                                <Label htmlFor="password">{t('domains.user.password')}</Label>
                                <Link to="/forgot-password" className="ml-auto text-sm text-blue-400 hover:underline">{t('login.forgotPassword')}</Link>
                            </div>
                            <Input id="password" type="password" {...register("password")} className="input-form"/>
                            {errors.password && <p className="text-red-500 text-sm">{errors.password.message}</p>}
                        </div>
                        <Button type="submit" className="button-green" disabled={isSubmitting}>
                            {t('login.submit')}
                        </Button>
                    </form>
                </CardContent>
                <CardFooter />
            </Card>
    );
}

