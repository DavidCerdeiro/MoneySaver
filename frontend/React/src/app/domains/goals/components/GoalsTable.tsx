import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/app/domains/shared/components/table";
import { useTranslation } from "react-i18next";
import type { GoalData } from "../schemas/Goal";
type GoalsTableProps = {
  goals: GoalData[];
  month?: number;
  year?: number;
  isView: boolean;
};
function getPercentColor(percent: number) {
  if (percent <= 25) return "text-green-500";
  if (percent <= 50) return "text-yellow-400";
  if (percent <= 75) return "text-orange-400";
  if (percent <= 100) return "text-red-500";
  return "text-red-700 font-bold"; // > 100
}

export function GoalsTable({ goals, month, year, isView }: GoalsTableProps) {
  const { t } = useTranslation();

  return (
    <div className="table-container">
      <Table>
        <TableCaption className="table-caption">
          {isView ? t('domains.goal.view.tableCaption', { month, year }) : t('domains.goal.add.tableCaption')}
        </TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead className="table-head">
              <span className="span-text font-medium">
                {t('domains.goal.name')}
              </span>
            </TableHead>
            <TableHead className="table-head">
              <span className="span-text font-medium">
                {t('domains.goal.category')}
              </span>
            </TableHead>
            <TableHead className="table-head">
              <span className="span-text font-medium">
                {t('domains.category.totalSpending')}
              </span>
            </TableHead>
            <TableHead className="table-head">
              <span className="span-text font-medium">
                {t('domains.goal.targetAmount')}
              </span>
            </TableHead>
            <TableHead className="table-head">
              <span className="span-text font-medium">
                {t('domains.goal.percent')}
              </span>
            </TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {goals.map((goal) => (
            <TableRow key={goal.id}>
              <TableCell className="table-cell">
                <span className="span-text">
                  {goal.name}
                </span>
              </TableCell>
              <TableCell className="table-cell">
                <span className="span-text">
                  {goal.nameCategory}
                </span>
              </TableCell>
              <TableCell className="table-cell-money">
                <span className="span-text">
                  {goal.amountCategory?.toFixed(2)} €
                </span>
              </TableCell>
              <TableCell className="table-cell-money">
                <span className="span-text">
                  {goal.targetAmount?.toFixed(2)} €
                </span>
              </TableCell>
              <TableCell className={`text-left ${getPercentColor(goal.percent ?? 0)}`}>
                <span className="span-text">
                  {goal.percent?.toFixed(2)}%
                </span>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}