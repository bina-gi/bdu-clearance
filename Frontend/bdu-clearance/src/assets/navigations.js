import {
  Building,
  FileCheck,
  Home,
  IdCard,
  Layers,
  Shield,
  Package,
  Users,
  ClipboardList,
} from "lucide-react";

export const navigations = [
  {
    title: "Dashboard",
    href: "/",
    roles: ["ADMIN", "STAFF", "STUDENT", "ADVISOR"],
    orgUnits: null, // null means all org units
    icon: Home,
  },

  // Student Pages
  {
    title: "Clearance",
    href: "/clearance",
    roles: ["STUDENT"],
    orgUnits: null,
    icon: FileCheck,
  },
  {
    title: "Report Lost Card",
    href: "/report-lost-card",
    roles: ["STUDENT"],
    orgUnits: null,
    icon: IdCard,
  },

  // Staff & Advisor Pages (shared)
  {
    title: "Requested Clearances",
    href: "/requested-clearances",
    roles: ["STAFF", "ADVISOR"],
    orgUnits: null,
    icon: ClipboardList,
  },

  // Org-specific pages for STAFF (Library, Cafeteria, etc.)
  {
    title: "Lost Card Reports",
    href: "/lost-card-reports",
    roles: ["STAFF"],
    orgUnits: ["LIBRARY", "CAFETERIA"], // Only these org units see this
    icon: IdCard,
  },
  {
    title: "Property",
    href: "/property",
    roles: ["STAFF"],
    orgUnits: ["LIBRARY", "CAFETERIA", "REGISTRAR"], // Only these org units see this
    icon: Package,
  },

  // Admin Pages
  {
    title: "User Management",
    href: "/user-management",
    roles: ["ADMIN"],
    orgUnits: null,
    icon: Users,
  },
  {
    title: "Roles",
    href: "/roles",
    roles: ["ADMIN"],
    orgUnits: null,
    icon: Shield,
  },
  {
    title: "Organizational Unit",
    href: "/organizational-unit",
    roles: ["ADMIN"],
    orgUnits: null,
    icon: Building,
  },
  {
    title: "Organization Types",
    href: "/organization-types",
    roles: ["ADMIN"],
    orgUnits: null,
    icon: Layers,
  },
];

/**
 * Filter navigations based on user role and organizational unit
 * @param {string} role - User's role (ADMIN, STAFF, STUDENT, ADVISOR)
 * @param {string|null} orgUnit - User's organizational unit (e.g., LIBRARY, CAFETERIA)
 * @returns {Array} Filtered navigation items
 */
export const getNavigationsForUser = (role, orgUnit) => {
  if (!role) return [];

  return navigations.filter((nav) => {
    // Check if user's role is allowed
    const roleAllowed = nav.roles.includes(role);
    if (!roleAllowed) return false;

    // If nav has orgUnits restriction, check if user's orgUnit matches
    if (nav.orgUnits !== null) {
      return orgUnit && nav.orgUnits.includes(orgUnit);
    }

    return true;
  });
};
